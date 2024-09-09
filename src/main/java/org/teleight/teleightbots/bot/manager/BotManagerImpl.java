package org.teleight.teleightbots.bot.manager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.bot.BotProvider;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.bot.TelegramBotImpl;
import org.teleight.teleightbots.bot.settings.BotSettings;
import org.teleight.teleightbots.updateprocessor.LongPollingUpdateProcessor;
import org.teleight.teleightbots.updateprocessor.UpdateProcessor;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public final class BotManagerImpl implements BotManager {

    private final List<TelegramBot> registeredBots = new CopyOnWriteArrayList<>();
    private final BotProvider botProvider = TelegramBotImpl::new;

    @Override
    public void registerLongPolling(@NotNull String token, @NotNull String username, @NotNull BotSettings botSettings, @NotNull Consumer<TelegramBot> completeCallback) {
        // If the bot username starts with a @, then we trim it.
        // Telegram bot usernames must not contain the at symbol
        if (username.startsWith("@")) {
            username = username.substring(1);
        }

        final UpdateProcessor updateProcessor = new LongPollingUpdateProcessor();
        final TelegramBot bot = botProvider.provide(token, username, updateProcessor, botSettings);

        updateProcessor.setBot(bot);
        updateProcessor.start().whenComplete((user, throwable) -> {
            if (throwable != null) {
                System.out.println("Error while starting the bot: " + bot.getBotUsername());
                if (botSettings.silentlyThrowMethodExecution()) {
                    TeleightBots.getExceptionManager().handleException(throwable);
                }
                bot.shutdown();
                return;
            }

            if (botSettings.extensionsEnabled()) {
                bot.getExtensionManager().start();
            }

            registeredBots.add(bot);
            try {
                completeCallback.accept(bot);
            } catch (Throwable t) {
                TeleightBots.getExceptionManager().handleException(t);
            }
        });
    }

    @Override
    public @NotNull @Unmodifiable Collection<TelegramBot> getRegisteredBots() {
        return registeredBots;
    }

    @Override
    public void close() throws IOException {
        for (final TelegramBot registeredBot : registeredBots) {
            registeredBot.shutdown();
        }
    }

}
