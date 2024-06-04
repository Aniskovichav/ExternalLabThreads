package by.itstep.aniskovich.java.externallabthreads.port.contoller;

import by.itstep.aniskovich.java.externallabthreads.port.model.logic.Port;
import by.itstep.aniskovich.java.externallabthreads.port.model.logic.Ship;
import by.itstep.aniskovich.java.externallabthreads.port.util.ShipInit;
import by.itstep.aniskovich.java.externallabthreads.port.view.PortLogger;

public class Main {
    public static void main(String[] args) {
        Port port = new Port(100,2);

        for (int i = 1; i <= 200; i++) {
            new Ship(i, port, ShipInit.randomShipCapacity(),
                    ShipInit.randomShipContainer());
        }

        PortLogger.log("All ships have completed their operations.");
    }
}
