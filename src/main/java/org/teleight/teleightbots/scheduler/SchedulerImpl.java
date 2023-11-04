package org.teleight.teleightbots.scheduler;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.TeleightBots;

import java.io.IOException;
import java.util.concurrent.*;

public class SchedulerImpl implements Scheduler {

    /*
    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(10, 200, 60, TimeUnit.SECONDS,
            new SynchronousQueue<>(), r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("Task Scheduler - #" + thread.getId());
            return thread;
        });

    private static final ScheduledExecutorService SCHEDULED = Executors.newScheduledThreadPool(10, r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            thread.setName("Task Scheduler Timer - #" + thread.getId());
            return thread;
        });
    */

    private static final ExecutorService EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();
    private static final ScheduledExecutorService SCHEDULED = Executors.newScheduledThreadPool(10, Thread.ofVirtual().factory());

    public SchedulerImpl() {
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
                TeleightBots.getExceptionManager().handleException(t);
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
    public void close() throws IOException {
        System.out.println("Closing Scheduler");

        EXECUTOR.shutdown();
        SCHEDULED.shutdown();
        try {
            EXECUTOR.awaitTermination(30, TimeUnit.SECONDS);
            SCHEDULED.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
