package org.teleight.teleightbots.event.trait;

/**
 * Interface for a cancellable event.
 */
public interface CancellableEvent extends Event {

    /**
     * Checks if the event is cancelled.
     *
     * @return a boolean indicating whether the event is cancelled
     */
    boolean isCancelled();

    /**
     * Sets the cancelled status of the event.
     *
     * @param value the new cancelled status of the event
     */
    void setCancelled(boolean value);

}
