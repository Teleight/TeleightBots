package org.teleight.teleightbots.bot.trait;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.methods.chat.GetChatMember;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.api.objects.chat.member.ChatMember;
import org.teleight.teleightbots.bot.Bot;
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

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

/**
 * Generic interface representing a Telegram Bot.
 * <p>
 * This interface provides methods to interact with the bot and its components.
 * It also provides methods to send requests to the Telegram Bot API.
 * <br>
 * This interface is by default implemented by the {@link org.teleight.teleightbots.bot.Bot} class.
 * </p>
 *
 * @see org.teleight.teleightbots.bot.Bot
 */
public interface TelegramBot {

    /**
     * Connects the bot to the Telegram Bot API.
     * <p>
     * This method should be called after the bot has been initialized and configured.
     * </p>
     */
    void connect();

    /**
     * Returns the bot's token. The token is used to authenticate the bot with the Telegram Bot API.
     *
     * @return the bot's token
     */
    @NotNull String getBotToken();

    /**
     * Returns the bot's username. The username is the unique identifier of the bot.
     *
     * @return the bot's username
     */
    @NotNull String getBotUsername();

    /**
     * Returns the bot's scheduler.
     * <p>
     * The scheduler is responsible for scheduling tasks to be executed at a later time.
     * It is also responsible for executing tasks in a separate thread.
     * </p>
     *
     * @return the bot's scheduler
     */
    @NotNull Scheduler getScheduler();

    /**
     * Returns the bot's update processor.
     * <p>
     * The update processor is responsible for processing incoming updates from the Telegram Bot API.
     * It is also responsible for sending requests to the Telegram Bot API.
     * </p>
     *
     * @return the bot's update processor
     */
    @NotNull UpdateProcessor getUpdateProcessor();

    /**
     * Returns the bot's settings.
     * <p>
     * The bot's settings contain various configuration options for the bot.
     * </p>
     *
     * @return the bot's settings
     */
    @NotNull BotSettings getBotSettings();

    /**
     * Returns the bot's event manager.
     * <p>
     * The event manager is responsible for managing events and event listeners.
     * </p>
     *
     * @return the bot's event manager
     */
    @NotNull EventManager getEventManager();

    /**
     * Returns the bot's menu manager.
     * <p>
     * The menu manager is responsible for managing menus and menu items.
     * </p>
     *
     * @return the bot's menu manager
     */
    @NotNull MenuManager getMenuManager();

    /**
     * Creates a new menu with the given name and builder.
     * <p>
     * The builder is used to create the menu items and sub-menus of the menu.
     * </p>
     *
     * @param builder the builder used to create the menu items and sub-menus
     * @return the created menu
     */
    default @NotNull Menu createMenu(@NotNull Menu.Builder builder) {
        return createMenu(null, builder);
    }

    /**
     * Creates a new menu with the given name and builder.
     * <p>
     * The builder is used to create the menu items and sub-menus of the menu.
     * </p>
     *
     * @param name    the name of the menu
     * @param builder the builder used to create the menu items and sub-menus
     * @return the created menu
     */
    @NotNull Menu createMenu(@Nullable String name, @NotNull Menu.Builder builder);

    /**
     * Returns the bot's command manager.
     * <p>
     * The command manager is responsible for managing commands and command handlers.
     * </p>
     *
     * @return the bot's command manager
     */
    @NotNull CommandManager getCommandManager();

    /**
     * Returns the bot's extension manager.
     * <p>
     * The extension manager is responsible for managing extensions and extension handlers.
     * </p>
     *
     * @return the bot's extension manager
     */
    @NotNull ExtensionManager getExtensionManager();

    /**
     * Returns the bot's file downloader.
     * <p>
     * The file downloader is responsible for downloading files from the Telegram Bot API.
     * </p>
     *
     * @return the bot's file downloader
     */
    @NotNull FileDownloader getFileDownloader();

    /**
     * Returns the bot's conversation manager.
     * <p>
     * The conversation manager is responsible for managing conversations and conversation handlers.
     * </p>
     *
     * @return the bot's conversation manager
     */
    @NotNull ConversationManager getConversationManager();

    /**
     * Sends a request to the Telegram Bot API using the given method.
     *
     * @param method the method used to create the request
     * @param <R>    the type of the expected response
     * @return a future representing the result of the request
     */
    <R extends Serializable> @NotNull CompletableFuture<R> execute(@NotNull ApiMethod<R> method);

    /**
     * Returns the chat member with the given user id in the given chat.
     *
     * @param chat the chat object
     * @param user the user object
     * @return a future representing the result of the request
     */
    default @NotNull CompletableFuture<ChatMember> getUser(@NotNull Chat chat, @NotNull User user) {
        return getUser(chat, user.id());
    }

    /**
     * Returns the chat member with the given user id in the given chat.
     *
     * @param chat   the chat object
     * @param userId the user id
     * @return a future representing the result of the request
     */
    default @NotNull CompletableFuture<ChatMember> getUser(@NotNull Chat chat, long userId) {
        return getUser(chat.id().toString(), userId);
    }

    /**
     * Returns the chat member with the given user id in the given chat.
     *
     * @param chatId the chat id
     * @param user   the user object
     * @return a future representing the result of the request
     */
    default @NotNull CompletableFuture<ChatMember> getUser(@NotNull String chatId, @NotNull User user) {
        return getUser(chatId, user.id());
    }

    /**
     * Returns the chat member with the given user id in the given chat.
     *
     * @param chatId the chat id
     * @param userId the user id
     * @return a future representing the result of the request
     */
    default @NotNull CompletableFuture<ChatMember> getUser(@NotNull String chatId, long userId) {
        final GetChatMember chatMember = GetChatMember.builder().chatId(chatId).userId(userId).build();
        return execute(chatMember);
    }

}
