package org.teleight.teleightbots.bot.trait;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.methods.chat.GetChatMember;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.api.objects.chat.member.ChatMember;
import org.teleight.teleightbots.bot.BotSettings;
import org.teleight.teleightbots.commands.CommandManager;
import org.teleight.teleightbots.conversation.ConversationManager;
import org.teleight.teleightbots.event.EventManager;
import org.teleight.teleightbots.extensions.ExtensionManager;
import org.teleight.teleightbots.files.FileDownloader;
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

    default @NotNull Menu createMenu(@NotNull Menu.Builder builder) {
        return createMenu(null, builder);
    }

    @NotNull Menu createMenu(@Nullable String name, @NotNull Menu.Builder builder);

    @NotNull CommandManager getCommandManager();

    @NotNull ExtensionManager getExtensionManager();

    @NotNull FileDownloader getFileDownloader();

    @NotNull ConversationManager getConversationManager();

    <R> @NotNull CompletableFuture<R> execute(@NotNull ApiMethod<R> method);

    default @NotNull CompletableFuture<ChatMember> getUser(@NotNull Chat chat, @NotNull User user) {
        return getUser(chat, user.id());
    }

    default @NotNull CompletableFuture<ChatMember> getUser(@NotNull Chat chatId, long userId) {
        return getUser(chatId.id().toString(), userId);
    }

    default @NotNull CompletableFuture<ChatMember> getUser(@NotNull String chatId, @NotNull User user) {
        return getUser(chatId, user.id());
    }

    default @NotNull CompletableFuture<ChatMember> getUser(@NotNull String chatId, long userId) {
        final GetChatMember chatMember = GetChatMember.builder().chatId(chatId).userId(userId).build();
        return execute(chatMember);
    }

}
