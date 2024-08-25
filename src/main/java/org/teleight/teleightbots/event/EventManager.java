package org.teleight.teleightbots.event;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.event.trait.Event;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * The {@code EventManager} interface manages event listeners and event handling.
 */
public sealed interface EventManager permits EventManagerImpl {

    /**
     * Registers an {@link EventListener} with the EventManager.
     * <p>
     * The EventListener will be invoked when events of the specified type occur.
     * </p>
     *
     * @param listener The {@link EventListener} to be added to the EventManager.
     *                 It should be capable of handling events of type {@code T}.
     * @param <T>      The type of event that the {@link EventListener} is designed to handle.
     * @return The current {@link EventManager} instance.
     */
    <T extends Event> @NotNull EventManager addListener(@NotNull EventListener<T> listener);

    /**
     * Registers an {@link EventListener} with the EventManager using a {@link Consumer}.
     *
     * <p>
     * This method simplifies the process of creating and registering a new EventListener by directly
     * providing the event type and the consumer that will handle the events. The consumer is invoked
     * when events of the specified type occur.
     * This is not recommended in case you need more control over your listener.
     * </p>
     *
     * @param eventType The {@link Class} of the event that the EventListener will handle.
     * @param listener  The {@link Consumer} that will process events of the specified type.
     *                  This consumer will be called when events of the specified type are dispatched.
     * @param <E>       The type of event that the EventListener will handle.
     * @return The current {@link EventManager} instance.
     * @see EventManager#addListener(EventListener) if you need more control over your listener.
     */
    <E extends Event> @NotNull EventManager addListener(@NotNull Class<E> eventType, @NotNull Consumer<E> listener);

    /**
     * Triggers an event and returns a {@link CompletableFuture} representing the processed event itself.
     *
     * @param event The event to be triggered.
     *              This event will be dispatched to all registered listeners of its type.
     * @param <T>   The type of event being triggered.
     * @return A {@link CompletableFuture} that will be completed with the event result.
     */
    <T extends Event> @NotNull CompletableFuture<T> call(@NotNull T event);

}
