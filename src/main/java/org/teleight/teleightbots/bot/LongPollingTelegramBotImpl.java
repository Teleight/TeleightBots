package org.teleight.teleightbots.bot;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.bot.settings.LongPollingBotSettings;
import org.teleight.teleightbots.commands.CommandManager;
import org.teleight.teleightbots.conversation.ConversationManager;
import org.teleight.teleightbots.event.EventManager;
import org.teleight.teleightbots.extensions.ExtensionManager;
import org.teleight.teleightbots.files.FileDownloader;
import org.teleight.teleightbots.menu.MenuManager;
import org.teleight.teleightbots.scheduler.Scheduler;
import org.teleight.teleightbots.updateprocessor.BotMethodExecutor;
import org.teleight.teleightbots.updateprocessor.LongPollingUpdateProcessor;
import org.teleight.teleightbots.updateprocessor.UpdateProcessor;

final class LongPollingTelegramBotImpl implements LongPollingTelegramBot {

    private final String token;
    private final String username;

    //Settings
    private final LongPollingBotSettings botSettings;

    //Updates
    private final UpdateProcessor updateProcessor;
    private final BotMethodExecutor botMethodExecutor;

    //Scheduler
    private final Scheduler scheduler = Scheduler.newScheduler();

    //Events
    private final EventManager eventManager = EventManager.newEventManager();

    //Menus
    private final MenuManager menuManager = MenuManager.newMenuManager();

    //Commands
    private final CommandManager commandManager = CommandManager.newCommandManager(this);

    //Conversations
    private final ConversationManager conversationManager = ConversationManager.newConversationManager(this);

    //Extensions
    private final ExtensionManager extensionManager = ExtensionManager.newExtensionManager(this);

    //FileDownloader
    private final FileDownloader fileDownloader = FileDownloader.newFileDownloader(this);

    LongPollingTelegramBotImpl(@NotNull String token,
                               @NotNull String username,
                               @NotNull LongPollingBotSettings botSettings) {
        this.token = token;
        this.username = username;
        this.botSettings = botSettings;
        this.updateProcessor = new LongPollingUpdateProcessor(this);
        this.botMethodExecutor = new BotMethodExecutor(this);
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
    public @NotNull LongPollingBotSettings getBotSettings() {
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
    public @NotNull CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public @NotNull ConversationManager getConversationManager() {
        return conversationManager;
    }

    @Override
    public BotMethodExecutor getBotMethodExecutor() {
        return botMethodExecutor;
    }

    @Override
    public UpdateProcessor getUpdateProcessor() {
        return updateProcessor;
    }

    @Override
    public @NotNull ExtensionManager getExtensionManager() {
        return extensionManager;
    }

    @Override
    public @NotNull FileDownloader getFileDownloader() {
        return fileDownloader;
    }

}
