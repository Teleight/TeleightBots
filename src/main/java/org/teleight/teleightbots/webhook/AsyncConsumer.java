package org.teleight.teleightbots.webhook;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * A functional interface that represents an asynchronous variant of the standard {@link Consumer}.
 *
 * <p>
 * The {@link AsyncConsumer} extends the {@link Consumer} interface
 * by providing an asynchronous mechanism to handle a given input.
 * </p>
 *
 * @param <T> The type of the input to the operation.
 * @see Consumer
 * @see CompletableFuture
 */
@FunctionalInterface
public interface AsyncConsumer<T> extends Consumer<T> {

    /**
     * Accepts the input asynchronously. This method should contain the logic
     * for processing the input asynchronously.
     *
     * @param t The input argument. Cannot be null.
     */
    void acceptAsync(@NotNull T t);

    /**
     * Accepts the input by running {@link #acceptAsync(Object)} asynchronously using a
     * {@link CompletableFuture}.
     *
     * <p>
     * This default implementation schedules the `acceptAsync` method to be executed
     * in a separate thread, without blocking the caller.
     * </p>
     *
     * @param t The input argument. Cannot be null.
     */
    @Override
    default void accept(@NotNull T t) {
        CompletableFuture.runAsync(() -> acceptAsync(t), Executors.newVirtualThreadPerTaskExecutor());
    }
}
