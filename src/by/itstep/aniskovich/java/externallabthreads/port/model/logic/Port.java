package by.itstep.aniskovich.java.externallabthreads.port.model.logic;

import by.itstep.aniskovich.java.externallabthreads.port.model.entity.Dock;
import by.itstep.aniskovich.java.externallabthreads.port.view.PortLogger;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    private final int capacity;
    private int currentContainers;
    private final Dock[] docks;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    
    public Port(int capacity, int dockCount) {
        this.capacity = capacity;
        this.currentContainers = 0;
        this.docks = new Dock[dockCount];
        for (int i = 0; i < dockCount; i++) {
            docks[i] = new Dock();
        }
    }
    
    public boolean loadContainer(int count) throws InterruptedException {
        lock.lock();
        try {
            while (currentContainers + count > capacity) {
                notFull.await();
            }
            currentContainers += count;
            PortLogger.log("Loaded " + count + " containers. " +
                    "Current containers: " + currentContainers);
            notEmpty.signalAll();
            return true;
        } finally {
            lock.unlock();
        }
    }

    public boolean unloadContainer(int count) throws InterruptedException {
        lock.lock();
        try {
            while (currentContainers < count) {
                notFull.await();
            }
            currentContainers -= count;
            PortLogger.log("Unloaded " + count + " containers. " +
                    "Current containers: " + currentContainers);
            notFull.signalAll();
            return true;
        } finally {
            lock.unlock();
        }
    }

    public Dock requestDock() thows InterruptedException {
        lock.lock();
        try {
            for (Dock dock : docks) {
                if (dock.tryLock()) {
                    return dock;
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public void releaseDock(Dock dock) {
            lock.unlock();
    }
}
