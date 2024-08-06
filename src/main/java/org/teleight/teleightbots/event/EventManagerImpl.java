package org.teleight.teleightbots.event;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.event.trait.Event;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

public final class EventManagerImpl implements EventManager {

    private final Map<Class<? extends Event>, Queue<EventListener<? extends Event>>> listeners = new ConcurrentHashMap<>();

    @Override
    public <T extends Event> @NotNull EventManager addListener(@NotNull EventListener<T> listener) {
        listeners.computeIfAbsent(listener.eventType(), k -> new ConcurrentLinkedQueue<>()).add(listener);
        return this;
    }

    @Override
    public <E extends Event> @NotNull EventManager addListener(@NotNull Class<E> eventType, @NotNull Consumer<E> listener) {
        return addListener(EventListener.of(eventType, listener));
    }

    @Override
    public <T extends Event> @NotNull CompletableFuture<T> call(@NotNull T event) {
        Queue<EventListener<? extends Event>> eventListeners = listeners.get(event.getClass());
        if (eventListeners == null || eventListeners.isEmpty()) {
            return CompletableFuture.completedFuture(event);
        }
        return CompletableFuture.supplyAsync(() -> {
            for (EventListener<? extends Event> eventListener : eventListeners) {
                @SuppressWarnings("unchecked")
                EventListener<T> listener = (EventListener<T>) eventListener;
                EventListener.Result result = listener.run(event);
                if (result == EventListener.Result.EXPIRED) {
                    listeners.get(event.getClass()).remove(eventListener);
                }
            }
            return event;
        }, ForkJoinPool.commonPool());
    }

}
