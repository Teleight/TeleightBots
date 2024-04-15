package org.teleight.teleightbots.event;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.event.trait.Event;
import org.teleight.teleightbots.utils.ArrayUtils;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class EventManagerImpl implements EventManager {

    private EventListener<Event>[] registeredEvents = new EventListener[0];

    @Override
    public <T extends Event> @NotNull EventManager addListener(@NotNull EventListener<T> listener) {
        this.registeredEvents = (EventListener<Event>[]) ArrayUtils.add(registeredEvents, listener);
        return this;
    }

    @Override
    public <E extends Event> @NotNull EventManager addListener(@NotNull Class<E> eventType, @NotNull Consumer<E> listener) {
        return addListener(EventListener.of(eventType, listener));
    }

    @Override
    public <T extends Event> @NotNull CompletableFuture<T> call(@NotNull T event) {
        return CompletableFuture.supplyAsync(() -> {
            boolean eventMapHasChanged = false;
            for (int i = 0; i < registeredEvents.length; i++) {
                final EventListener<Event> eventListener = registeredEvents[i];
                if (eventListener.eventType() != event.getClass()) {
                    continue;
                }

                final EventListener.Result result = eventListener.run(event);
                if (result == EventListener.Result.EXPIRED) {
                    eventMapHasChanged = true;
                    registeredEvents[i] = null;
                }
            }

            if (eventMapHasChanged) {
                int length = registeredEvents.length;
                final EventListener<Event>[] newArray = new EventListener[length];
                int newIndex = 0;

                for (int i = 0; i < length; i++) {
                    if (registeredEvents[i] == null) {
                        continue;
                    }
                    newArray[newIndex++] = registeredEvents[i];
                }

                registeredEvents = newArray;
            }
            return event;
        });
    }

}
