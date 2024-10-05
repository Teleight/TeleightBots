package org.teleight.teleightbots.bot;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.methods.GetChatMember;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.ChatMember;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.settings.BotSettings;
import org.teleight.teleightbots.commands.CommandManager;
import org.teleight.teleightbots.conversation.ConversationManager;
import org.teleight.teleightbots.event.EventManager;
import org.teleight.teleightbots.event.bot.MethodSendEvent;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;
import org.teleight.teleightbots.extensions.ExtensionManager;
import org.teleight.teleightbots.files.FileDownloader;
import org.teleight.teleightbots.menu.Menu;
import org.teleight.teleightbots.menu.MenuBuilder;
import org.teleight.teleightbots.menu.MenuImpl;
import org.teleight.teleightbots.menu.MenuManager;
import org.teleight.teleightbots.scheduler.Scheduler;
import org.teleight.teleightbots.updateprocessor.BotMethodExecutor;
import org.teleight.teleightbots.updateprocessor.UpdateProcessor;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

/**
 * Generic interface representing a Telegram Bot.
 * <p>
 * This interface provides methods to interact with the bot and its components.
 * It also provides methods to send requests to the Telegram Bot API.
 * <br>
 * This interface is by default implemented by the {@link LongPollingTelegramBot} class.
 * </p>
 *
 * @see LongPollingTelegramBot
 */
public sealed interface TelegramBot permits LongPollingTelegramBot, WebhookTelegramBot {

    /**
     * Closes the bot from the Telegram Bot API.
     * <p>
     * This method will also close all attached processors to the specified bot instance
     * </p>
     */
    void shutdown();

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
     * @param builder the builder used to create the menu items and submenus
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
     * @param builder the builder used to create the menu items and submenus
     * @return the created menu
     */
    default @NotNull Menu createMenu(@Nullable String name, @NotNull Menu.Builder builder){
        final var menuBuilder = new MenuBuilder.MenuBuilderImpl();
        final var rootMenu = menuBuilder.createMenu(name);
        builder.create(menuBuilder, rootMenu);

        for (final MenuImpl subMenu : menuBuilder.getAllMenus()) {
            subMenu.createKeyboard();

            getMenuManager().registerMenu(subMenu);
        }

        return rootMenu;
    }

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

    BotMethodExecutor getBotMethodExecutor();

    UpdateProcessor getUpdateProcessor();

    /**
     * Sends a request to the Telegram Bot API using the given method.
     *
     * @param method the method used to create the request
     * @param <R>    the type of the expected response
     * @return a future representing the result of the request
     */
    default <R extends Serializable> @NotNull CompletableFuture<R> execute(@NotNull ApiMethod<R> method){
        final var responseFuture = getBotMethodExecutor().executeMethod(method);
        return responseFuture.thenCompose(responseJson -> {
            try {
                final R result = method.deserializeResponse(responseJson);
                getEventManager().call(new MethodSendEvent<>(this, method, result));
                return CompletableFuture.completedFuture(result);
            } catch (TelegramRequestException e) {
                if (!getBotSettings().silentlyThrowMethodExecution()) {
                    TeleightBots.getExceptionManager().handleException(e);
                }
                return CompletableFuture.failedFuture(e);
            }
        });
    }

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
        return getUser(chat.id(), userId);
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
        final GetChatMember chatMember = GetChatMember.ofBuilder(chatId, userId).build();
        return execute(chatMember);
    }

}
