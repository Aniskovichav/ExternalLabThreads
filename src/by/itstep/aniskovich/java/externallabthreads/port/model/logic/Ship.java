package by.itstep.aniskovich.java.externallabthreads.port.model.logic;

import by.itstep.aniskovich.java.externallabthreads.port.view.PortLogger;

import java.util.concurrent.TimeUnit;

public class Ship implements Runnable {
    private final int id;
    private final Port port;
    private final int capacity;
    private int containerCount;

    public Ship(int id, Port port, int capacity, int containerCount) {
        this.id = id;
        this.port = port;
        this.capacity = capacity;
        this.containerCount = containerCount;
        new Thread(this).start();
    }

    @Override
    public void run() {
        PortLogger.log("Ship " + id + " started with " + containerCount
                + " containers.");
        while (true) { //
            int remainingSpaceStoragePort = port.getCapacityStorage()
                    - port.getCurrentContainerCount();
            int dockIndex = port.requestDock();
            if (dockIndex >= 0 && remainingSpaceStoragePort > 0) {
                try {
                    PortLogger.log("Ship " + id + " docked at dock " + dockIndex
                            + " with " + containerCount + " containers");
                    if (containerCount > 0) {
                        int containersToUnload = Math.min(containerCount,
                                remainingSpaceStoragePort);
                        port.loadContainerInStorage(containersToUnload);
                        PortLogger.log("Ship " + id + " unloaded "
                                + containersToUnload + " containers.");
                        containerCount -= containersToUnload;
                        PortLogger.log("Ship " + id + " with " + containerCount
                                + " containers.");
                    } else if (containerCount == 0) {
                        int containersToLoad = Math.min(capacity,
                                port.getCapacityStorage());
                        port.unloadContainerFromStorage(containersToLoad);
                        PortLogger.log("Ship " + id + " loaded "
                                + containersToLoad + " containers.");
                        containerCount += containersToLoad;
                        PortLogger.log("Ship " + id + " with " + containerCount
                                + " containers.");
                    }
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    PortLogger.log("Ship " + id + " finished loading/unloading.");
                    port.releaseDock(dockIndex);
                } finally {
                    port.releaseDock(dockIndex);
                }
                break;
            } else {
                exchangeContainerWithAnotherShip();
            }
        }
    }

    private void exchangeContainerWithAnotherShip() {
        Ship[] ships = getOtherShips();
        for (Ship other : ships) {
            if (other != this && other.id != this.id
                    && port.getLock().tryLock()) {
                try {
                    if (this.containerCount > 0 && other.containerCount == 0) {
                        other.containerCount += this.containerCount;
                        PortLogger.log("Ship " + this.id + " exchanged "
                                + this.containerCount + " containers with ship "
                                + other.id + ".");
                        this.containerCount = 0;
                    } else if (this.containerCount == 0
                            && other.containerCount > 0) {
                        this.containerCount += other.containerCount;
                        PortLogger.log("Ship " + this.id + " exchanged "
                                + other.containerCount + " containers with ship "
                                + other.id + ".");
                        other.containerCount = 0;
                    }
                } finally {
                    port.getLock().unlock();
                }
            }
        }
    }

    private Ship[] getOtherShips() {
        return new Ship[0];
    }

    public int getContainerCount() {
        return containerCount;
    }

    public int getId() {
        return id;
    }
}
