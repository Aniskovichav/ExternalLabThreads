package by.itstep.aniskovich.java.port.model.logic;

import by.itstep.aniskovich.java.externallabthreads.port.model.logic.Port;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PortTest {
    private Port port;

    @Before
    public void setUp() {
        port = new Port(100, 2);
    }

    @Test
    public void testLoadContainers() {
        assertTrue(port.loadContainer(50));
        assertTrue(port.loadContainer(50));
        assertFalse(port.loadContainer(1));
    }

    @Test
    public void testUnloadContainers() {
        port.loadContainer(100);
        assertTrue(port.unloadContainer(50));
        assertTrue(port.unloadContainer(50));
        assertFalse(port.unloadContainer(1));
    }

    @Test
    public void testDockRequest() {
        int dockIndex = port.requestDock();
        assertTrue(dockIndex >= 0);
        port.releaseDock(dockIndex);
    }

    @Test
    public void testMultipleDocks() {
        int dockIndex1 = port.requestDock();
        int dockIndex2 = port.requestDock();

        assertTrue(dockIndex1 >= 0);
        assertTrue(dockIndex2 >= 0);
        assertEquals(-1, port.requestDock()); // Проверяем, что больше нет доступных доков

        port.releaseDock(dockIndex1);
        port.releaseDock(dockIndex2);

        assertTrue(port.requestDock() >= 0); // Проверяем, что док снова доступен
    }
}
