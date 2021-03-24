package dev.valcom.dhs.api;

import lombok.Getter;

public abstract class DependentService<T> {

    public enum Priority {
        HIGHEST(2),
        HIGH(1),
        NORMAL(0),
        LOW(-1),
        LOWEST(-2);

        @Getter private int value;

        Priority(int value) {
            this.value = value;
        }
    }

    @Getter private String category;
    @Getter private Priority priority = Priority.NORMAL;
    private T hook;

    public DependentService(String category) {
        this.category = category;
    }

    public DependentService(String category, Priority priority) {
        this.category = category;
        this.priority = priority;
    }

    public void start() {}

    public void shutdown() {}

    protected abstract T generateHook();

    public final void regenerateHook() {
        hook = generateHook();
    }
}
