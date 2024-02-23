package org.teleight.teleightbots.botmanager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.bot.BotSettings;
import org.teleight.teleightbots.bot.trait.LongPollingBot;
import org.teleight.teleightbots.bot.trait.TelegramBot;
import org.teleight.teleightbots.bot.trait.WebHookBot;
import org.teleight.teleightbots.updateprocessor.LongPollingUpdateProcessor;
import org.teleight.teleightbots.updateprocessor.UpdateProcessor;
import org.teleight.teleightbots.utils.ArrayUtils;

import java.util.function.Consumer;

public final class BotManagerImpl implements BotManager {

    private Bot[] registeredBots = new Bot[0];
    private BotProvider botProvider = Bot::new;

    public BotManagerImpl() {
    }

    @Override
    public void registerLongPolling(@NotNull String token, @NotNull String username, @Nullable BotSettings botSettings) {
        registerLongPolling(token, username, botSettings, null);
    }

    @Override
    public void registerLongPolling(@NotNull String token, @NotNull String username, @Nullable BotSettings botSettings, @Nullable Consumer<TelegramBot> completeCallback) {
        registerBot(LongPollingBot.class, token, username, botSettings, completeCallback);
    }

    private <T extends TelegramBot> void registerBot(@NotNull Class<T> botType, @NotNull String token, @NotNull String username,
                                                     @Nullable BotSettings botSettings, @Nullable Consumer<TelegramBot> completeCallback) {
        if (botType == WebHookBot.class) {
            throw new IllegalArgumentException("WebHook bots are currently not supported");
        }

        if (username.startsWith("@")) {
            username = username.substring(1);
        }

        UpdateProcessor updateProcessor = null;
        if (botType == LongPollingBot.class) {
            updateProcessor = new LongPollingUpdateProcessor();
        }
        final Bot bot = botProvider.provide(token, username, updateProcessor, botSettings);
        updateProcessor.setBot(bot);
        bot.connect();

        registeredBots = ArrayUtils.add(registeredBots, bot);

        if (completeCallback != null) {
            try {
                completeCallback.accept(bot);
            } catch (Throwable t) {
                TeleightBots.getExceptionManager().handleException(t);
            }
        }
    }

    public void close() {
        System.out.println("Closing BotManager");

        for (final Bot registeredBot : registeredBots) {
            registeredBot.close();
        }
    }

    @Override
    public BotProvider getBotProvider() {
        return botProvider;
    }

    @Override
    public void setBotProvider(@NotNull BotProvider botProvider) {
        this.botProvider = botProvider;
    }

    @Override
    public Bot[] getBots() {
        return registeredBots;
    }

}
