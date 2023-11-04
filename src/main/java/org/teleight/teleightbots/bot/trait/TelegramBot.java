package org.teleight.teleightbots.bot.trait;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.pagination.Menu;
import org.teleight.teleightbots.api.pagination.PaginationManager;
import org.teleight.teleightbots.bot.BotSettings;
import org.teleight.teleightbots.event.EventManager;
import org.teleight.teleightbots.scheduler.Scheduler;
import org.teleight.teleightbots.updateprocessor.UpdateProcessor;

public interface TelegramBot {

    void connect();

    @NotNull String getBotToken();

    @NotNull String getBotUsername();

    @NotNull Scheduler getScheduler();

    @NotNull UpdateProcessor getUpdateProcessor();

    @NotNull BotSettings getBotSettings();

    @NotNull EventManager getEventManager();

    @NotNull PaginationManager getPaginationManager();

    @NotNull Menu createMenu(@NotNull Menu.Builder builder);

}
