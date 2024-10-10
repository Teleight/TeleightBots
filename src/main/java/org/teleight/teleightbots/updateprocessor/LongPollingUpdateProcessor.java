package org.teleight.teleightbots.updateprocessor;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.api.methods.GetMe;
import org.teleight.teleightbots.api.methods.GetUpdates;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.LongPollingTelegramBot;
import org.teleight.teleightbots.bot.settings.LongPollingBotSettings;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

public final class LongPollingUpdateProcessor implements UpdateProcessor {

    private final CountDownLatch processorLatch = new CountDownLatch(1);

    private final LongPollingTelegramBot bot;
    private final LongPollingBotSettings settings;

    private Thread updateProcessorThread;
    private int lastReceivedUpdate = 0;

    public LongPollingUpdateProcessor(@NotNull LongPollingTelegramBot bot) {
        this.bot = bot;
        this.settings = bot.getBotSettings();
    }

    @Override
    public @NotNull CompletableFuture<User> start() {
        updateProcessorThread = new UpdateProcessorThread();
        updateProcessorThread.setName(bot.getBotUsername() + " Update Processor");
        updateProcessorThread.start();

        return bot.execute(new GetMe())
                .whenComplete((user, throwable) -> {
                    if (throwable != null) {
                        System.out.println("Error while authenticating the bot: " + bot.getBotUsername());
                        if (bot.getBotSettings().silentlyThrowMethodExecution()) {
                            TeleightBots.getExceptionManager().handleException(throwable);
                        }
                        bot.shutdown();
                        return;
                    }
                    System.out.println("Bot authenticated: " + user.username());
                    processorLatch.countDown();
                });
    }

    @Override
    public void close() {
        updateProcessorThread.interrupt();
    }

    private void executeGetUpdates() {
        final var getUpdates = GetUpdates.ofBuilder()
                .timeout(settings.updatesTimeout())
                .limit(settings.updatesLimit())
                .offset(lastReceivedUpdate + 1)
                .build();

        final String responseJson = bot.getBotMethodExecutor().executeMethod(getUpdates)
                .exceptionally(throwable -> {
                    TeleightBots.getExceptionManager().handleException(throwable);
                    return null;
                })
                .join();
        if (responseJson == null) {
            return;
        }

        Update[] updates;
        try {
            updates = getUpdates.deserializeResponse(responseJson);
        } catch (TelegramRequestException e) {
            TeleightBots.getExceptionManager().handleException(e);
            return;
        }

        if (updates.length == 0) {
            return;
        }

        handleUpdates(updates, responseJson);
    }

    private void handleUpdates(@NotNull Update[] updates, @NotNull String responseJson) {
        // Mark updates for removal
        int newSize = 0;
        for (int i = 0; i < updates.length; i++) {
            Update update = updates[i];
            if (update.updateId() >= lastReceivedUpdate) {
                updates[newSize] = update;
                newSize++;
            }
        }

        // Compact the array
        if (newSize < updates.length) {
            Update[] newUpdates = new Update[newSize];
            System.arraycopy(updates, 0, newUpdates, 0, newSize);
            updates = newUpdates;
        }

        // Process the updates
        for (final Update update : updates) {
            handleNewUpdate(bot, update, responseJson);
        }

        // Update lastReceivedUpdate
        if (updates.length > 0) {
            lastReceivedUpdate = updates[updates.length - 1].updateId();
        }
    }

    private class UpdateProcessorThread extends Thread {
        @Override
        public void run() {
            try {
                processorLatch.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
            while (!Thread.currentThread().isInterrupted()) {
                executeGetUpdates();
            }
        }
    }

}
