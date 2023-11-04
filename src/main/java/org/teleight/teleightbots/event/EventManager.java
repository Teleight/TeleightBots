package org.teleight.teleightbots.event;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.event.trait.Event;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface EventManager {

    <T extends Event> @NotNull EventManager addListener(@NotNull EventListener<T> listener);

    <E extends Event> @NotNull EventManager addListener(@NotNull Class<E> eventType, @NotNull Consumer<E> listener);

    <T extends Event> @NotNull CompletableFuture<T> call(@NotNull T event);

}
