package org.teleight.teleightbots.scheduler;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;

/// Represents a scheduler that can be used to schedule tasks to be executed at a later time.
public interface Scheduler extends Closeable {

    /// Internal factory method for creating a new scheduler instance.
    ///
    /// This method should only be used internally and should not be accessed directly
    /// by external users of the API.
    ///
    /// @return A new instance of `SchedulerImpl`.
    @ApiStatus.Internal
    static @NotNull Scheduler newScheduler() {
        return new SchedulerImpl();
    }

    /// Creates a new task builder.
    /// @param task the task to be executed
    /// @return a new task builder
    default @NotNull Task.Builder buildTask(@NotNull Runnable task) {
        return new Task.Builder(this, task);
    }

    /// Submits a task to be executed at a later time or repeatedly.
    /// @param builder the task builder
    /// @return the submitted task
    @NotNull Task submitTask(@NotNull Task.Builder builder);

}
