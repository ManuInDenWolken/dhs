package dev.valcom.dhs.core.util;

import dev.valcom.dhs.api.DependentServiceLocator;
import dev.valcom.dhs.core.exceptions.ServiceNotAvailableException;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class ServiceUtils {

    private static final String REQUIRED_SERVICE_NOT_AVAILABLE =
        "No service of category %category is available, although one is required";

    public static void requireServiceOrElse(DependentServiceLocator serviceLocator, String category, Runnable orElse) {
        if (!serviceLocator.getService(category).isPresent()) orElse.run();
    }

    public static void requireServiceOrShutdown(DependentServiceLocator serviceLocator, String category) {
        requireServiceOrElse(serviceLocator, category, () -> {
            logGlobal(Level.SEVERE, REQUIRED_SERVICE_NOT_AVAILABLE.replace("%category", category));
            serviceLocator.shutdown();
            System.exit(-1);
        });
    }

    public static void requireServiceOrError(DependentServiceLocator serviceLocator, String category) {
        requireServiceOrElse(serviceLocator, category, () -> {
            throw new ServiceNotAvailableException(REQUIRED_SERVICE_NOT_AVAILABLE.replace("%category", category));
        });
    }

    private static void logGlobal(Level level, String message) {
        Logger.getGlobal().log(level, message);
    }

}
