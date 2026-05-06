package org.teleight.teleightbots.event;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.event.trait.Event;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/// Interface that manages event listeners and event handling.
public sealed interface EventManager permits EventManagerImpl {

    @ApiStatus.Internal
    static @NotNull EventManager newEventManager() {
        return new EventManagerImpl();
    }

    /// Registers an [EventListener] with the EventManager.
    ///
    /// The EventListener will be invoked when events of the specified type occur.
    ///
    /// @param listener The [EventListener] to be added to the EventManager.
    ///                 It should be capable of handling events of type `T`.
    /// @param <T>      The type of event that the [EventListener] is designed to handle.
    /// @return The current [EventManager] instance.
    <T extends Event> @NotNull EventManager addListener(@NotNull EventListener<T> listener);

    /// Registers an [EventListener] with the EventManager using a [Consumer].
    ///
    /// This method simplifies the process of creating and registering a new EventListener by directly
    /// providing the event type and the consumer that will handle the events. The consumer is invoked
    /// when events of the specified type occur.
    /// This is not recommended in case you need more control over your listener.
    ///
    /// @param eventType The [Class] of the event that the EventListener will handle.
    /// @param listener  The [Consumer] that will process events of the specified type.
    ///                  This consumer will be called when events of the specified type are dispatched.
    /// @param <E>       The type of event that the EventListener will handle.
    /// @return The current [EventManager] instance.
    /// @see EventManager#addListener(EventListener) if you need more control over your listener.
    <E extends Event> @NotNull EventManager addListener(@NotNull Class<E> eventType, @NotNull Consumer<E> listener);

    /// Triggers an event and returns a [CompletableFuture] representing the processed event itself.
    ///
    /// @param event The event to be triggered.
    ///              This event will be dispatched to all registered listeners of its type.
    /// @param <T>   The type of event being triggered.
    /// @return A [CompletableFuture] that will be completed with the event result.
    <T extends Event> @NotNull CompletableFuture<T> call(@NotNull T event);

}
