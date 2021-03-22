package dev.valcom.dhs.api;

import dev.valcom.dhs.api.concurrency.ConcurrencyHandler;
import dev.valcom.dhs.api.concurrency.ConcurrencyHandlers;

import java.util.Optional;
import java.util.concurrent.Callable;

public interface DependentServiceLocator {

    void start(ConcurrencyHandler handler);

    default void start() {
        start(ConcurrencyHandlers.DEFAULT);
    }

    void shutdown(ConcurrencyHandler handler);

    default void shutdown() {
        shutdown(ConcurrencyHandlers.DEFAULT);
    }

    void registerService(Callable<DependentService<?>> serviceCallable);

    Optional<DependentService<?>> getService(String category);

}
