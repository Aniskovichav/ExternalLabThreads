package by.itstep.aniskovich.java.port.model.logic;

import by.itstep.aniskovich.java.externallabthreads.port.model.logic.Port;
import by.itstep.aniskovich.java.externallabthreads.port.model.logic.Ship;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShipTest {
    private Port port;
    private Ship ship1;
    private Ship ship2;
    private Ship ship3;

    @Before
    public void setUp() {
        port = new Port(100, 3);
    }

    @Test
    public void testShipDockingAndLoading() throws InterruptedException {
        ship1 = new Ship(1, port, 50, 0);
        Thread thread1 = new Thread(ship1);
        thread1.start();

        thread1.join();
    }

    @Test
    public void testShipDockingAndUnloading() throws InterruptedException {
        port.loadContainerInStorage(50);
        ship2 = new Ship(2, port, 50, 0);
        Thread thread2 = new Thread(ship2);
        thread2.start();

        thread2.join();
    }

    @Test
    public void testShipExchangingContainers() throws InterruptedException {
        ship1 = new Ship(1, port, 50, 50);
        ship2 = new Ship(2, port, 50, 0);

        Thread thread1 = new Thread(ship1);
        Thread thread2 = new Thread(ship2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    @Test
    public void testShipQueueing() throws InterruptedException {
        port = new Port(100, 1);

        ship1 = new Ship(1, port, 50, 50);
        ship2 = new Ship(2, port, 50, 0);
        ship3 = new Ship(3, port, 50, 0);

        Thread thread1 = new Thread(ship1);
        Thread thread2 = new Thread(ship2);
        Thread thread3 = new Thread(ship3);

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }

}
