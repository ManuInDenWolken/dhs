package dev.valcom.dhs.api.concurrency;

public interface ConcurrencyHandler {

    void handle(Runnable runnable);

}
