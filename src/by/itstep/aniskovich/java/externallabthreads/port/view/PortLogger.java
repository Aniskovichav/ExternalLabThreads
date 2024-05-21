package by.itstep.aniskovich.java.externallabthreads.port.view;

import org.apache.log4j.Logger;

public class PortLogger {
    private static final Logger LOGGER = Logger.getLogger(PortLogger.class);

    public static void log(String message) {
        LOGGER.info(message);
    }

    public static void logError(String message) {
        LOGGER.error(message);
    }
}
