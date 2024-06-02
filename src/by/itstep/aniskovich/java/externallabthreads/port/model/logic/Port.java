package by.itstep.aniskovich.java.externallabthreads.port.model.logic;

import by.itstep.aniskovich.java.externallabthreads.port.view.PortLogger;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private final int capacityStorage;
    private int currentContainers;
    private final Dock[] docks;
    private final Lock containerLock;
//    private final LinkedBlockingQueue<Ship> waitingShips;

    {
        containerLock = new ReentrantLock();
//        waitingShips = new LinkedBlockingQueue<>();
    }

    public Port(int capacityStorage, int dockCount) {
        this.capacityStorage = capacityStorage;
        this.currentContainers = 0;
        this.docks = new Dock[dockCount];
        for (int i = 0; i < dockCount; i++) {
            docks[i] = new Dock();
        }
    }

    public Lock getLock() {
        return containerLock;
    }

    public boolean loadContainerInStorage(int count) {
        containerLock.lock();
        try {
            while (currentContainers + count > capacityStorage) {
                return false;
            }
            currentContainers += count;
            PortLogger.log("Loaded in storage " + count + " containers. " +
                    "Current containers: " + currentContainers);
            return true;
        } finally {
            containerLock.unlock();
        }
    }

    public boolean unloadContainerFromStorage(int count) {
        containerLock.lock();
        try {
            while (currentContainers < count) {
                return false;
            }
            currentContainers -= count;
            PortLogger.log("Unloaded from storage" + count + " containers. " +
                    "Current containers: " + currentContainers);
            return true;
        } finally {
            containerLock.unlock();
        }
    }

    public int requestDock() {
        for (int i = 0; i < docks.length; i++) {
            if (docks[i].lock.tryLock()) {
                docks[i].occupied = true;
                return i;
            }
        }
        return -1;
    }

    public void releaseDock(int dockIndex) {
        docks[dockIndex].occupied = false;
        docks[dockIndex].lock.unlock();
    }

//    public void addWaitingShip(Ship ship) {
//        waitingShips.offer(ship);
//    }
//
//    public Ship getWaitingShip() {
//        return waitingShips.poll();
//    }

    public int getCapacityStorage() {
        return capacityStorage;
    }

    public int getCurrentContainerCount() {
        return currentContainers;
    }

    private static class Dock {
        final Lock lock =  new ReentrantLock();
        boolean occupied = false;
    }
}
