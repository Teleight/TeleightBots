package org.teleight.teleightbots.event;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.event.trait.Event;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Represents an event listener.
 * <p>
 * This interface provides methods to handle events of a specific type.
 * <br>
 * This interface is by default implemented by the {@link EventListenerImpl} class.
 * </p>
 *
 * @param <T> The type of event that this EventListener is for.
 * @see EventListenerImpl
 */
public sealed interface EventListener<T extends Event> permits EventListenerImpl {

    /**
     * This is a static method that creates a new Builder for an EventListener.
     *
     * @param eventType The type of event for which the EventListener is being built.
     * @return A new Builder instance.
     */
    static <T extends Event> Builder<T> builder(@NotNull Class<T> eventType) {
        return new EventListenerImpl.BuilderImpl<>(eventType);
    }

    /**
     * This is a static method that creates a new EventListener.
     *
     * @param eventType The type of event for which the EventListener is being built.
     * @param listener  The Consumer that will handle the event.
     * @return A new EventListener instance.
     */
    static <T extends Event> EventListener<T> of(@NotNull Class<T> eventType, @NotNull Consumer<T> listener) {
        return builder(eventType).handler(listener).build();
    }

    /**
     * Returns the type of event that this EventListener is for.
     *
     * @return The Class of the event type.
     */
    @NotNull Class<T> eventType();

    /**
     * Runs the event.
     *
     * @param event The event to run.
     * @return The result of running the event.
     */
    @NotNull Result run(@NotNull T event);

    /**
     * This is an enumeration of possible results of running an event.
     */
    enum Result {
        SUCCESS,
        CANCELLED,
        EXPIRED,
        EXCEPTION
    }

    sealed interface Builder<T extends Event> permits EventListenerImpl.BuilderImpl {
        /**
         * Sets a filter for the Builder.
         *
         * @param filter The Predicate to use as a filter.
         * @return The Builder instance.
         */
        @NotNull Builder<T> filter(@NotNull Predicate<T> filter);

        /**
         * Sets whether the Builder should ignore cancelled events.
         *
         * @param ignoreCancelled Whether to ignore cancelled events.
         * @return The Builder instance.
         */
        @NotNull Builder<T> ignoreCancelled(boolean ignoreCancelled);

        /**
         * Sets the handler for the Builder.
         *
         * @param handler The Consumer to use as a handler.
         * @return The Builder instance.
         */
        @NotNull Builder<T> handler(@NotNull Consumer<T> handler);

        /**
         * Sets the expiry count for the Builder.
         *
         * @param expireCount The number of times an event can expire before it is no longer handled.
         * @return The Builder instance.
         */
        @NotNull Builder<T> expireCount(int expireCount);

        /**
         * Builds the EventListener.
         *
         * @return A new EventListener instance.
         */
        @NotNull EventListener<T> build();
    }

}
