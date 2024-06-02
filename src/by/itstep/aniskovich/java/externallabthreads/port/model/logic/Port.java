package by.itstep.aniskovich.java.externallabthreads.port.model.logic;

import by.itstep.aniskovich.java.externallabthreads.port.view.PortLogger;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private final int capacity;
    private int currentContainers;
    private final boolean[] docks;
    private final Lock lock;
    private final LinkedBlockingQueue<Ship> waitingShips;

    {
        lock = new ReentrantLock();
        waitingShips = new LinkedBlockingQueue<>();
    }

    public Port(int capacity, int dockCount) {
        this.capacity = capacity;
        this.currentContainers = 0;
        this.docks = new boolean[dockCount];
    }

    public Lock getLock() {
        return lock;
    }

    public boolean loadContainer(int count) {
        lock.lock();
        try {
            if (currentContainers + count > capacity) {
                return false;
            }
            currentContainers += count;
            PortLogger.log("Loaded " + count + " containers. " +
                    "Current containers: " + currentContainers);
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean unloadContainer(int count) {
        lock.lock();
        try {
            if (currentContainers < count) {
                return false;
            }
            currentContainers -= count;
            PortLogger.log("Unloaded " + count + " containers. " +
                    "Current containers: " + currentContainers);
            return true;
        } finally {
            lock.unlock();
        }
    }

    public int requestDock() {
        lock.lock();
        try {
            for (int i = 0; i < docks.length; i++) {
                if (!docks[i]) {
                    docks[i] = true;
                    return i;
                }
            }
            return -1;
        } finally {
            lock.unlock();
        }
    }

    public void releaseDock(int dockIndex) {
        lock.lock();
        try {
            if (dockIndex >= 0 && dockIndex < docks.length) {
                docks[dockIndex] = false;
            }
        } finally {
            lock.unlock();
        }
    }

    public void addWaitingShip(Ship ship) {
        waitingShips.offer(ship);
    }

    public Ship getWaitingShip() {
        return waitingShips.poll();
    }
}
