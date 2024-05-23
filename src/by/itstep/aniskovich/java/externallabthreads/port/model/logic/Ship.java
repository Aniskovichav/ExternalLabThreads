package by.itstep.aniskovich.java.externallabthreads.port.model.logic;

import by.itstep.aniskovich.java.externallabthreads.port.view.PortLogger;

public class Ship implements Runnable {
    private final int id;
    private final Thread thread;
    private int containerCount;
    private final Port port;
    private final int capacity;

    public Ship(int id, Port port, int capacity, int containerCount) {
        this.id = id;
        this.thread = new Thread(this);
        this.port = port;
        this.capacity = capacity;
        this.containerCount = containerCount;
        this.thread.start();
//        this.thread.join();
    }

    @Override
    public void run() {
        PortLogger.log("Ship " + id + " started.");
        while (true) {
            if (port.getLock().tryLock()) {
                try {
                    int dockIndex = -1;
                    while (dockIndex == -1) {
                        dockIndex = port.requestDock();
                        if (dockIndex == -1) {
                            Thread.sleep(100);
                        }
                    }

                    PortLogger.log("Ship " + id + " docked with " + containerCount + " containers");
                    if (containerCount > 0) {
                        port.unloadContainer(containerCount);
                        containerCount = 0;
                    } else {
                        port.loadContainer(capacity);
                        containerCount = capacity;
                    }

                    PortLogger.log("Ship " + id + " finished loading/unloading.");
                    port.releaseDock(dockIndex);
                    break;
                } catch (InterruptedException e) {
                    PortLogger.logError("Ship " + id + "interrupted " + e.getMessage());
                } finally {
                    port.getLock().unlock();
                }
            }
        }
    }
}
