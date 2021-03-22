package dev.valcom.dhs.api.concurrency;

import static lombok.AccessLevel.*;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class ConcurrencyHandlers {

    public static final ConcurrencyHandler DEFAULT = new DefaultConcurrencyHandler();
    public static final ConcurrencyHandler NO_CONCURRENCY = new NoConcurrencyHandler();

}
