package by.itstep.aniskovich.java.externallabthreads.port.contoller;

import by.itstep.aniskovich.java.externallabthreads.port.model.logic.Port;
import by.itstep.aniskovich.java.externallabthreads.port.model.logic.Ship;
import by.itstep.aniskovich.java.externallabthreads.port.view.PortLogger;

public class Main {
    public static void main(String[] args) {
        Port port = new Port(100,2);

        Ship ship1 = new Ship(1, port,50, 10);
        Ship ship2 = new Ship(2, port,50, 50);
        Ship ship3 = new Ship(3, port,150, 100);



        PortLogger.log("All ships have completed their operations.");
    }
}
