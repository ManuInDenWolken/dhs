package dev.valcom.dhs.core;

import dev.valcom.dhs.api.DependentService;
import dev.valcom.dhs.api.DependentServiceLocator;
import dev.valcom.dhs.api.concurrency.ConcurrencyHandler;

import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DefaultServiceLocator implements DependentServiceLocator {

    private Map<String, DependentService<?>> services = new Hashtable<>();

    @Override
    public void start(ConcurrencyHandler handler) {
        handler.handle(() -> services.values().forEach(DependentService::start));
    }

    @Override
    public void shutdown(ConcurrencyHandler handler) {
        handler.handle(() -> services.values().forEach(DependentService::start));
    }

    @Override
    public void registerService(Callable<DependentService<?>> serviceCallable) {
        checkNotNull(serviceCallable, "Callable may not be null");
        DependentService<?> service;
        try {
            service = serviceCallable.call();
        } catch (Exception exception) {
            log(Level.INFO, "Registered service " + exception.getMessage()
                + " is not available.");
            return;
        }
        service.regenerateHook();
        String category = service.getCategory();
        if (!getService(category).isPresent()) services.put(category, service);
        services.put(category, compare(service, getService(category).get()));
    }

    @Override
    public Optional<DependentService<?>> getService(String category) {
        return Optional.ofNullable(services.get(category));
    }

    private static DependentService<?> compare(DependentService<?> firstService, DependentService<?> secondService) {
        return firstService.getPriority().getValue() > secondService.getPriority().getValue()
            ? firstService : secondService;
    }

    private static <T> void checkNotNull(T object, String message) {
        if (object == null) throw new NullPointerException(message);
    }

    private static void log(Level level, String message) {
        Logger.getGlobal().log(level, message);
    }
}
