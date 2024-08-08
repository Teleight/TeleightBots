package org.teleight.teleightbots.event;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.event.trait.CancellableEvent;
import org.teleight.teleightbots.event.trait.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class EventListenerImpl<T extends Event> implements EventListener<T> {

    private final Class<T> eventType;
    private final List<Predicate<T>> filters;
    private final boolean ignoreCancelled;
    private final Consumer<T> handler;
    private final AtomicInteger expireCount;

    public EventListenerImpl(Class<T> eventType, List<Predicate<T>> filters, boolean ignoreCancelled, Consumer<T> handler, int expireCount) {
        this.eventType = eventType;
        this.filters = filters;
        this.ignoreCancelled = ignoreCancelled;
        this.handler = handler;
        this.expireCount = new AtomicInteger(expireCount);
    }

    @Override
    public @NotNull Class<T> eventType() {
        return eventType;
    }

    @Override
    public @NotNull Result run(@NotNull T event) {
        if (ignoreCancelled && event instanceof CancellableEvent && ((CancellableEvent) event).isCancelled()) {
            return Result.CANCELLED;
        }

        for (Predicate<T> filter : filters) {
            if (!filter.test(event)) {
                return Result.CANCELLED;
            }
        }

        if (handler != null) {
            try {
                handler.accept(event);
            } catch (Exception e) {
                TeleightBots.getExceptionManager().handleException(e);
                return Result.EXCEPTION;
            }
        }

        final boolean hasExpirationCount = expireCount.get() > 0;
        if (hasExpirationCount && expireCount.decrementAndGet() == 0) {
            return Result.EXPIRED;
        }

        return Result.SUCCESS;
    }

    static final class BuilderImpl<T extends Event> implements Builder<T> {
        private final Class<T> eventType;
        private final List<Predicate<T>> filters = new ArrayList<>();
        private boolean ignoreCancelled = true;
        private Consumer<T> handler;
        private int expireCount;

        BuilderImpl(@NotNull Class<T> eventType) {
            this.eventType = eventType;
        }

        @Override
        public @NotNull Builder<T> filter(@NotNull Predicate<T> filter) {
            this.filters.add(filter);
            return this;
        }

        @Override
        public @NotNull Builder<T> ignoreCancelled(boolean ignoreCancelled) {
            this.ignoreCancelled = ignoreCancelled;
            return this;
        }

        @Override
        public @NotNull Builder<T> handler(@NotNull Consumer<T> handler) {
            this.handler = handler;
            return this;
        }

        @Override
        public @NotNull Builder<T> expireCount(int expireCount) {
            this.expireCount = expireCount;
            return this;
        }

        @Override
        public @NotNull EventListener<T> build() {
            return new EventListenerImpl<>(eventType, filters, ignoreCancelled, handler, expireCount);
        }
    }

}
