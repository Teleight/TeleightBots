package org.teleight.teleightbots.event;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.event.trait.Event;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Represents a listener for handling events of a specific type.
 *
 * @param <T> The type of event that this EventListener is designed to handle.
 */
public sealed interface EventListener<T extends Event> permits EventListenerImpl {

    /**
     * Creates a new Builder for constructing an {@link EventListener}.
     *
     * @param eventType The class type of the event that the EventListener will handle.
     * @return A new Builder instance configured for the specified event type.
     */
    static @NotNull <T extends Event> Builder<T> ofBuilder(@NotNull Class<T> eventType) {
        return new EventListenerImpl.BuilderImpl<>(eventType);
    }

    /**
     * Creates a new {@link EventListener} instance with the specified event type and handler.
     *
     * @param eventType The class type of the event that the EventListener will handle.
     * @param listener  The {@link Consumer} that will process events of the specified type.
     * @return A new EventListener instance configured with the provided event type and handler.
     */
    static @NotNull <T extends Event> EventListener<T> of(@NotNull Class<T> eventType, @NotNull Consumer<T> listener) {
        return ofBuilder(eventType).handler(listener).build();
    }

    /**
     * Returns the class type of the event that this {@link EventListener} is designed to handle.
     *
     * @return The {@link Class} object representing the type of event this listener is for.
     */
    @NotNull Class<T> eventType();

    /**
     * Executes the event handler with the given event.
     *
     * @param event The event to be handled by the listener.
     * @return The result of processing the event, as defined by the {@link Result} enumeration.
     */
    @NotNull Result run(@NotNull T event);

    /**
     * Enumeration of possible results from running an event.
     */
    enum Result {
        /**
         * Indicates that the event was successfully processed.
         */
        SUCCESS,

        /**
         * Indicates that the event was canceled and not processed.
         */
        CANCELLED,

        /**
         * Indicates that the event expired before it could be processed.
         */
        EXPIRED,

        /**
         * Indicates that an exception occurred while processing the event.
         */
        EXCEPTION
    }

    /**
     * Builder interface for constructing {@link EventListener} instances.
     *
     * @param <T> The type of event that the {@link EventListener} being built will handle.
     */
    sealed interface Builder<T extends Event> permits EventListenerImpl.BuilderImpl {

        /**
         * Sets a filter for the builder to determine which events should be processed.
         *
         * @param filter The {@link Predicate} used to filter events.
         * @return The current Builder instance.
         */
        @NotNull Builder<T> filter(@NotNull Predicate<T> filter);

        /**
         * Configures whether the builder should ignore events that have been canceled.
         *
         * @param ignoreCancelled Whether to ignore canceled events.
         * @return The current Builder instance.
         */
        @NotNull Builder<T> ignoreCancelled(boolean ignoreCancelled);

        /**
         * Sets the handler for the builder to process events.
         *
         * @param handler The {@link Consumer} that processes the events.
         * @return The current Builder instance.
         */
        @NotNull Builder<T> handler(@NotNull Consumer<T> handler);

        /**
         * Sets the number of times an event can expire before it is no longer handled.
         *
         * <p>
         * Expiry count determines how many times an event can be considered expired
         * before it is discarded and marked as {@code EXPIRED}.
         * </p>
         *
         * @param expireCount The number of times an event can expire.
         * @return The current Builder instance.
         */
        @NotNull Builder<T> expireCount(int expireCount);

        /**
         * Builds and returns the {@link EventListener} instance configured with the specified settings.
         *
         * @return A new {@link EventListener} instance with the configured properties.
         */
        @NotNull EventListener<T> build();
    }

}
