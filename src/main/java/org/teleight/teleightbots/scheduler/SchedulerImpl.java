package org.teleight.teleightbots.scheduler;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.bot.Bot;

import java.io.IOException;
import java.util.concurrent.*;

public class SchedulerImpl implements Scheduler {

    private static final ExecutorService EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();
    private static final ScheduledExecutorService SCHEDULED = Executors.newScheduledThreadPool(10, Thread.ofVirtual().factory());

    private final Bot bot;

    public SchedulerImpl(Bot bot) {
        this.bot = bot;
    }

    @Override
    public @NotNull Task submitTask(Task.@NotNull Builder builder) {
        final long delayMillis = builder.delayMillis;
        final long repeatMillis = builder.repeatMillis;

        final boolean hasDelay = delayMillis != -1;
        final boolean hasRepeat = repeatMillis != -1;
        final Runnable preparedTask = () -> {
            try {
                builder.task.run();
            } catch (Throwable t) {
                TeleightBots.getExceptionManager().handleException(bot, t);
            }
        };

        final Future<?> futureTask;
        if (!hasDelay && !hasRepeat) {
            futureTask = EXECUTOR.submit(preparedTask);
        } else if (hasDelay && !hasRepeat) {
            futureTask = SCHEDULED.schedule(preparedTask, delayMillis, TimeUnit.MILLISECONDS);
        } else if (!hasDelay && hasRepeat) {
            futureTask = SCHEDULED.scheduleAtFixedRate(preparedTask, 0, repeatMillis, TimeUnit.MILLISECONDS);
        } else {
            futureTask = SCHEDULED.scheduleAtFixedRate(preparedTask, delayMillis, repeatMillis, TimeUnit.MILLISECONDS);
        }


        return new Task() {
            @Override
            public @NotNull Scheduler owner() {
                return builder.scheduler;
            }

            @Override
            public void cancel() {
                futureTask.cancel(true);
            }
        };
    }

    @Override
    public void close() {
        bot.getLogger().info("Shutting down bot scheduler and executor");

        EXECUTOR.shutdown();
        SCHEDULED.shutdown();
        try {
            boolean hasExecutorTerminated = EXECUTOR.awaitTermination(30, TimeUnit.SECONDS);
            boolean hasSchedulerTerminated = SCHEDULED.awaitTermination(30, TimeUnit.SECONDS);

            if (!hasSchedulerTerminated) {
                bot.getLogger().warn("Bot Scheduler did not terminate and reached timeout after 30 seconds");
            }
            if (!hasExecutorTerminated) {
                bot.getLogger().warn("Bot Executor did not terminate and reached timeout after 30 seconds");
            }
        } catch (InterruptedException e) {
            TeleightBots.getExceptionManager().handleException(bot, e);
        }
    }

}
