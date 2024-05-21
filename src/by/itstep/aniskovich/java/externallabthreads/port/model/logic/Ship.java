package by.itstep.aniskovich.java.externallabthreads.port.model.logic;

import by.itstep.aniskovich.java.externallabthreads.port.model.entity.Dock;
import by.itstep.aniskovich.java.externallabthreads.port.model.logic.Port;
import by.itstep.aniskovich.java.externallabthreads.port.view.PortLogger;

public class Ship implements Runnable {
    private final Port port;
    private final int capacity;
    private int containerCount;

    public Ship(Port port, int capacity, int containerCount) {
        this.port = port;
        this.capacity = capacity;
        this.containerCount = 0;
    }

    @Override
    public void run() {
        try {
            Dock dock = null;
            while (dock == null) {
                dock = port.requestDock();
                if (dock == null) {
                    Thread.sleep(100);
                }
            }

            PortLogger.log("Ship docked with " + containerCount + " containers");
            if (containerCount > 0) {
                port.unloadContainer(containerCount);
                containerCount = 0;
            } else {
                port.loadContainer(capacity);
                containerCount = capacity;
            }

            PortLogger.log("Ship finished loading/unloading.");
            port.releaseDock(dock);
        } catch (InterruptedException e) {
                PortLogger.logError("Ship interrupted " + e.getMessage());
        }
    }
}
