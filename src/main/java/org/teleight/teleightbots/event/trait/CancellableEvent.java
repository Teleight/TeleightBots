package org.teleight.teleightbots.event.trait;

public interface CancellableEvent extends Event {

    boolean isCancelled();

    void setCancelled(boolean value);

}
