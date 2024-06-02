package by.itstep.aniskovich.java.port.model.logic;

import by.itstep.aniskovich.java.externallabthreads.port.model.logic.Port;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PortTest {
    private Port port;

    @Before
    public void setUp() {
        port = new Port(100, 3);
    }

    @Test
    public void testLoadContainers() {
        assertTrue(port.loadContainerInStorage(50));
        assertTrue(port.loadContainerInStorage(50));
        assertFalse(port.loadContainerInStorage(1));
    }

    @Test
    public void testUnloadContainers() {
        port.loadContainerInStorage(100);
        assertTrue(port.unloadContainerFromStorage(50));
        assertTrue(port.unloadContainerFromStorage(50));
        assertFalse(port.unloadContainerFromStorage(1));
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
        int dockIndex3 = port.requestDock();

        assertTrue(dockIndex1 >= 0);
        assertTrue(dockIndex2 >= 0);
        assertTrue(dockIndex3 >= 0);
        assertEquals(-1, port.requestDock());

        port.releaseDock(dockIndex1);
        port.releaseDock(dockIndex2);
        port.releaseDock(dockIndex3);

        assertTrue(port.requestDock() >= 0);
    }
}
