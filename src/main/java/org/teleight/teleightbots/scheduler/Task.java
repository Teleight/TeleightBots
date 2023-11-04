package org.teleight.teleightbots.scheduler;

import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public interface Task {

    @NotNull Scheduler owner();

    void cancel();

    final class Builder {
        final Scheduler scheduler;
        final Runnable task;

        long delayMillis = -1;
        long repeatMillis = -1;

        Builder(@NotNull Scheduler scheduler, @NotNull Runnable task) {
            this.scheduler = scheduler;
            this.task = task;
        }

        public @NotNull Builder delay(@IntRange(from = 0L) long delay, @NotNull TimeUnit timeUnit) {
            this.delayMillis = timeUnit.toMillis(delay);
            return this;
        }

        public @NotNull Builder repeat(@IntRange(from = 0L) long repeat, @NotNull TimeUnit timeUnit) {
            this.repeatMillis = timeUnit.toMillis(repeat);
            return this;
        }

        public @NotNull Task schedule() {
            return scheduler.submitTask(this);
        }
    }

}
