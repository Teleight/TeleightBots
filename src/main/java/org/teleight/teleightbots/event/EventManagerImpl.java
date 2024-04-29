package org.teleight.teleightbots.event;


import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.event.trait.Event;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class EventManagerImpl implements EventManager {

    private final List<EventListener<? extends Event>> registeredEvents = new CopyOnWriteArrayList<>();

    @Override
    public <T extends Event> @NotNull EventManager addListener(@NotNull EventListener<T> listener) {
        this.registeredEvents.add(listener);
        return this;
    }

    @Override
    public <E extends Event> @NotNull EventManager addListener(@NotNull Class<E> eventType, @NotNull Consumer<E> listener) {
        return addListener(EventListener.of(eventType, listener));
    }

    @Override
    public <T extends Event> @NotNull CompletableFuture<T> call(@NotNull T event) {
        return CompletableFuture.supplyAsync(() -> {
            for (EventListener<? extends Event> registeredEvent : registeredEvents) {
                if (registeredEvent.eventType().isInstance(event)) {
                    @SuppressWarnings("unchecked") final EventListener<T> listener = (EventListener<T>) registeredEvent;
                    final EventListener.Result result = listener.run(event);
                    if (result == EventListener.Result.EXPIRED) {
                        registeredEvents.remove(registeredEvent);
                    }
                }
            }
            return event;
        });
    }

}
