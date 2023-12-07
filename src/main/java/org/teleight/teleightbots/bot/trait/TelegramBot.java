package org.teleight.teleightbots.bot.trait;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.methods.GetChatMember;
import org.teleight.teleightbots.api.objects.chat.member.ChatMember;
import org.teleight.teleightbots.bot.BotSettings;
import org.teleight.teleightbots.commands.CommandManager;
import org.teleight.teleightbots.event.EventManager;
import org.teleight.teleightbots.menu.Menu;
import org.teleight.teleightbots.menu.MenuManager;
import org.teleight.teleightbots.scheduler.Scheduler;
import org.teleight.teleightbots.updateprocessor.UpdateProcessor;

import java.util.concurrent.CompletableFuture;

public interface TelegramBot {

    void connect();

    @NotNull String getBotToken();

    @NotNull String getBotUsername();

    @NotNull Scheduler getScheduler();

    @NotNull UpdateProcessor getUpdateProcessor();

    @NotNull BotSettings getBotSettings();

    @NotNull EventManager getEventManager();

    @NotNull MenuManager getMenuManager();

    default @NotNull Menu createMenu(@NotNull Menu.Builder builder){
        return createMenu(null, builder);
    }

    @NotNull Menu createMenu(String name, @NotNull Menu.Builder builder);

    @NotNull CommandManager getCommandManager();

    <R> @NotNull CompletableFuture<R> execute(@NotNull ApiMethod<R> method);

    default @NotNull CompletableFuture<ChatMember> getUser(String chatId, long userId) {
        final GetChatMember chatMember = GetChatMember.builder().chatId(chatId).userId(userId).build();
        return execute(chatMember);
    }

}
