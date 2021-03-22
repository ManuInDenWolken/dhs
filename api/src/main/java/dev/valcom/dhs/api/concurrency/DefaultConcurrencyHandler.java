package dev.valcom.dhs.api.concurrency;

import static lombok.AccessLevel.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PACKAGE)
public final class DefaultConcurrencyHandler implements ConcurrencyHandler {

    @Override
    public void handle(Runnable runnable) {
        new Thread(runnable).start();
    }
}
