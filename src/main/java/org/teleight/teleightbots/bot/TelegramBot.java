package org.teleight.teleightbots.bot;

import org.jetbrains.annotations.ApiStatus;
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
     * Retrieves the bot's token used to authenticate with the Telegram Bot API.
     *
     * @return the authentication token of the bot
     */
    @NotNull String getBotToken();

    /**
     * Retrieves the username of the bot.
     *
     * @return the username of the bot
     */
    @NotNull String getBotUsername();

    /**
     * Provides access to the bot's task scheduler.
     *
     * @return the task scheduler associated with the bot
     */
    @NotNull Scheduler getScheduler();

    /**
     * Retrieves the bot's configuration settings.
     *
     * @return the settings of the bot
     */
    @NotNull BotSettings getBotSettings();

    /**
     * Provides access to the bot's event manager.
     *
     * @return the event manager associated with the bot
     */
    @NotNull EventManager getEventManager();

    /**
     * Provides access to the bot's menu manager.
     *
     * @return the menu manager associated with the bot
     */
    @NotNull MenuManager getMenuManager();

    /**
     * Creates a new menu using the provided builder.
     *
     * @param builder the builder to construct the menu
     * @return the created menu
     */
    default @NotNull Menu createMenu(@NotNull Menu.Builder builder) {
        return createMenu(null, builder);
    }

    /**
     * Creates a new menu with a specified name using the provided builder.
     *
     * @param name    the name of the menu (can be null)
     * @param builder the builder used to create the menu
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
     * Provides access to the bot's command manager.
     *
     * @return the command manager associated with the bot
     */
    @NotNull CommandManager getCommandManager();

    /**
     * Provides access to the bot's extension manager.
     *
     * @return the extension manager associated with the bot
     */
    @NotNull ExtensionManager getExtensionManager();

    /**
     * Provides access to the bot's file downloader.
     *
     * @return the file downloader associated with the bot
     */
    @NotNull FileDownloader getFileDownloader();

    /**
     * Provides access to the bot's conversation manager.
     * <p>
     * The conversation manager handles interactions with users and conversation flows.
     * </p>
     *
     * @return the conversation manager associated with the bot
     */
    @NotNull ConversationManager getConversationManager();

    @ApiStatus.Internal
    BotMethodExecutor getBotMethodExecutor();

    @ApiStatus.Internal
    UpdateProcessor getUpdateProcessor();

    /**
     * Sends a request to the Telegram Bot API using the given method.
     *
     * @param method the method used to create the request
     * @param <R>    the type of the expected response
     * @return a future representing the result of the request
     */
    default <R extends Serializable> @NotNull CompletableFuture<R> execute(@NotNull ApiMethod<R> method) {
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
