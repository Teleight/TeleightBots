package org.teleight.teleightbots.bot;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.bot.trait.TelegramBot;
import org.teleight.teleightbots.commands.CommandManager;
import org.teleight.teleightbots.commands.CommandManagerImpl;
import org.teleight.teleightbots.conversation.ConversationManager;
import org.teleight.teleightbots.conversation.ConversationManagerImpl;
import org.teleight.teleightbots.event.EventManager;
import org.teleight.teleightbots.event.EventManagerImpl;
import org.teleight.teleightbots.event.bot.MethodSendEvent;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;
import org.teleight.teleightbots.extensions.ExtensionManager;
import org.teleight.teleightbots.extensions.ExtensionManagerImpl;
import org.teleight.teleightbots.menu.*;
import org.teleight.teleightbots.scheduler.Scheduler;
import org.teleight.teleightbots.updateprocessor.UpdateProcessor;

import java.util.concurrent.CompletableFuture;

public class Bot implements TelegramBot {

    private final String token;
    private final String username;

    //Settings
    private final BotSettings botSettings;

    //Updates
    private final UpdateProcessor updateProcessor;

    //Scheduler
    private final Scheduler scheduler = Scheduler.newScheduler();

    //Events
    private final EventManager eventManager = new EventManagerImpl();

    //Menus
    private final MenuManager menuManager = new MenuManagerImpl();

    //Commands
    private final CommandManager commandManager = new CommandManagerImpl(this);

    //Conversations
    private final ConversationManager conversationManager = new ConversationManagerImpl(this);

    //Extensions
    private final ExtensionManager extensionManager = new ExtensionManagerImpl(this);

    public Bot(String token, String username, UpdateProcessor updateProcessor, BotSettings botSettings) {
        this.token = token;
        this.username = username;
        this.botSettings = botSettings;
        this.updateProcessor = updateProcessor;
    }

    @Override
    public @NotNull String getBotToken() {
        return token;
    }

    @Override
    public @NotNull String getBotUsername() {
        return username;
    }

    @Override
    public @NotNull Scheduler getScheduler() {
        return scheduler;
    }

    @Override
    public @NotNull UpdateProcessor getUpdateProcessor() {
        return updateProcessor;
    }

    @Override
    public @NotNull BotSettings getBotSettings() {
        return botSettings;
    }

    @Override
    public @NotNull EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public @NotNull MenuManager getMenuManager() {
        return menuManager;
    }

    @Override
    public @NotNull Menu createMenu(@Nullable String name, Menu.@NotNull Builder builder) {
        final MenuBuilder.MenuBuilderImpl menuBuilder = new MenuBuilder.MenuBuilderImpl();
        final Menu rootMenu = menuBuilder.createMenu(name);
        builder.create(menuBuilder, rootMenu);

        for (final MenuImpl subMenu : menuBuilder.getAllMenus()) {
            subMenu.createKeyboard();

            menuManager.registerMenu(subMenu);
        }

        return rootMenu;
    }

    @Override
    public @NotNull CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public @NotNull ConversationManager getConversationManager() {
        return conversationManager;
    }

    @Override
    public @NotNull ExtensionManager getExtensionManager() {
        return extensionManager;
    }

    @Override
    public void connect() {
        extensionManager.start();
        updateProcessor.start();
    }

    public void close() {
        try{
            extensionManager.shutdown();
        } catch (Exception e) {
            TeleightBots.getExceptionManager().handleException(e);
        }

        try {
            scheduler.close();
        } catch (Exception e) {
            TeleightBots.getExceptionManager().handleException(e);
        }

        try {
            updateProcessor.shutdown();
        } catch (Exception e) {
            TeleightBots.getExceptionManager().handleException(e);
        }
    }

    @Override
    public <R> @NotNull CompletableFuture<R> execute(@NotNull ApiMethod<R> method) {
        final CompletableFuture<String> responseFuture = updateProcessor.executeMethod(method);
        return responseFuture.thenApplyAsync(responseJson -> {
            final R result;
            try {
                result = method.deserializeResponse(responseJson);
            } catch (Exception e) {
                throw new TelegramRequestException(e);
            }
            eventManager.call(new MethodSendEvent<>(Bot.this, method, result));
            return result;
        });
    }

}
