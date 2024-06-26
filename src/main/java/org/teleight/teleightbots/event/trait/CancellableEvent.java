package org.teleight.teleightbots.event.trait;

/**
 * Interface for a cancellable event.
 */
public interface CancellableEvent extends Event {

    /**
     * Checks if the event is canceled.
     *
     * @return a boolean indicating whether the event is canceled
     */
    boolean isCancelled();

    /**
     * Sets the canceled status of the event.
     *
     * @param value the new canceled status of the event
     */
    void setCancelled(boolean value);

}
