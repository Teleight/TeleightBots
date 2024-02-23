package org.teleight.teleightbots.scheduler;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;

/**
 * Represents a scheduler that can be used to schedule tasks to be executed at a later time.
 * <p>
 * This interface provides methods to schedule tasks to be executed at a later time.
 * <br>
 * This interface is by default implemented by the {@link SchedulerImpl} class.
 * </p>
 *
 * @see SchedulerImpl
 */
public interface Scheduler extends Closeable {

    /**
     * Creates a new scheduler.
     * @return a new scheduler
     */
    static @NotNull Scheduler newScheduler() {
        return new SchedulerImpl();
    }

    /**
     * Creates a new task builder.
     * @param task the task to be executed
     * @return a new task builder
     */
    default @NotNull Task.Builder buildTask(@NotNull Runnable task) {
        return new Task.Builder(this, task);
    }

    /**
     * Submits a task to be executed at a later time or repeatedly.
     * @param builder the task builder
     * @return the submitted task
     */
    @NotNull Task submitTask(@NotNull Task.Builder builder);

}
