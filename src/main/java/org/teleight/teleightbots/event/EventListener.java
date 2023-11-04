package org.teleight.teleightbots.event;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.event.trait.Event;

import java.util.function.Consumer;
import java.util.function.Predicate;

public sealed interface EventListener<T extends Event> permits EventListenerImpl {

    static <T extends Event> Builder<T> builder(@NotNull Class<T> eventType) {
        return new EventListenerImpl.BuilderImpl<>(eventType);
    }

    static <T extends Event> EventListener<T> of(@NotNull Class<T> eventType, @NotNull Consumer<T> listener) {
        return builder(eventType).handler(listener).build();
    }

    @NotNull Class<T> eventType();

    @NotNull Result run(@NotNull T event);

    enum Result {
        SUCCESS,
        CANCELLED,
        EXPIRED,
        EXCEPTION
    }

    sealed interface Builder<T extends Event> permits EventListenerImpl.BuilderImpl {
        @NotNull Builder<T> filter(@NotNull Predicate<T> filter);

        @NotNull Builder<T> ignoreCancelled(boolean ignoreCancelled);

        @NotNull Builder<T> handler(@NotNull Consumer<T> handler);

        @NotNull Builder<T> expireCount(int expireCount);

        @NotNull EventListener<T> build();
    }

}
