package org.teleight.teleightbots.scheduler;

import org.jetbrains.annotations.NotNull;

import java.io.Closeable;

public interface Scheduler extends Closeable {

    static @NotNull Scheduler newScheduler() {
        return new SchedulerImpl();
    }

    default @NotNull Task.Builder buildTask(@NotNull Runnable task) {
        return new Task.Builder(this, task);
    }

    @NotNull Task submitTask(@NotNull Task.Builder builder);

}
