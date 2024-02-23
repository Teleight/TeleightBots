package org.teleight.teleightbots.event;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.event.trait.Event;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * This is an interface for an EventManager. It provides methods to add listeners and call events.
 */
public interface EventManager {

    /**
     * Adds an EventListener to the EventManager.
     *
     * @param listener The EventListener to be added.
     * @param <T>      The type of event that the EventListener is for.
     * @return The EventManager instance.
     */
    <T extends Event> @NotNull EventManager addListener(@NotNull EventListener<T> listener);

    /**
     * Adds an EventListener to the EventManager using a Consumer.
     *
     * @param eventType The type of event that the EventListener is for.
     * @param listener  The Consumer to be used as an EventListener.
     * @param <E>       The type of event that the EventListener is for.
     * @return The EventManager instance.
     */
    <E extends Event> @NotNull EventManager addListener(@NotNull Class<E> eventType, @NotNull Consumer<E> listener);

    /**
     * Calls an event and returns a CompletableFuture of the event.
     *
     * @param event The event to be called.
     * @param <T>   The type of event to be called.
     * @return A CompletableFuture of the event.
     */
    <T extends Event> @NotNull CompletableFuture<T> call(@NotNull T event);

}
