package by.itstep.aniskovich.java.externallabthreads.port.util;

import java.util.Random;

public class ShipInit {
    private static final Random RND;
    private static final int MAX_CAPACITY_SHIP = 20;
    private static final int MIN_CAPACITY_SHIP = 5;

    static {
        RND = new Random();
    }

    public static int randomShipCapacity() {
        return RND.nextInt(MIN_CAPACITY_SHIP,MAX_CAPACITY_SHIP) * 5;
    }

    public static int randomShipContainer() {
        return RND.nextInt(MIN_CAPACITY_SHIP, randomShipCapacity());
    }
}
