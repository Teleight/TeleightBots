package org.teleight.teleightbots.bot;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.bot.settings.WebhookBotSettings;
import org.teleight.teleightbots.bot.webhook.WebhookMessageHandler;
import org.teleight.teleightbots.commands.CommandManager;
import org.teleight.teleightbots.commands.CommandManagerImpl;
import org.teleight.teleightbots.conversation.ConversationManager;
import org.teleight.teleightbots.conversation.ConversationManagerImpl;
import org.teleight.teleightbots.event.EventManager;
import org.teleight.teleightbots.event.EventManagerImpl;
import org.teleight.teleightbots.event.bot.BotShutdownEvent;
import org.teleight.teleightbots.extensions.ExtensionManager;
import org.teleight.teleightbots.extensions.ExtensionManagerImpl;
import org.teleight.teleightbots.files.FileDownloader;
import org.teleight.teleightbots.files.FileDownloaderImpl;
import org.teleight.teleightbots.menu.MenuManager;
import org.teleight.teleightbots.menu.MenuManagerImpl;
import org.teleight.teleightbots.scheduler.Scheduler;
import org.teleight.teleightbots.updateprocessor.BotMethodExecutor;
import org.teleight.teleightbots.updateprocessor.UpdateProcessor;
import org.teleight.teleightbots.updateprocessor.WebhookUpdateProcessor;

public non-sealed class WebhookTelegramBot implements TelegramBot {

    private final String token;
    private final String username;

    //Settings
    private final WebhookBotSettings botSettings;

    //Updates
    private final UpdateProcessor updateProcessor;
    private final BotMethodExecutor botMethodExecutor;

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

    //FileDownloader
    private final FileDownloader fileDownloader = new FileDownloaderImpl(this);


    // Webhook
    private final WebhookMessageHandler webhookHandler;

    public WebhookTelegramBot(@NotNull String token,
                              @NotNull String username,
                              @NotNull WebhookBotSettings botSettings,
                              @NotNull WebhookMessageHandler webhookHandler) {
        this.token = token;
        this.username = username;
        this.botSettings = botSettings;
        this.updateProcessor = new WebhookUpdateProcessor(this);
        this.botMethodExecutor = new BotMethodExecutor(this);
        this.webhookHandler = webhookHandler;
    }

    public @NotNull WebhookMessageHandler getWebhookBotInfo() {
        return webhookHandler;
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
    public @NotNull WebhookBotSettings getBotSettings() {
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

    @Override
    public void shutdown() {
        eventManager.call(new BotShutdownEvent(this));

        execute(this.webhookHandler.createDeleteWebhook());

        try {
            if (botSettings.extensionsEnabled()) {
                extensionManager.close();
            }
            scheduler.close();
            updateProcessor.close();
            fileDownloader.close();
        } catch (Exception e) {
            TeleightBots.getExceptionManager().handleException(e);
        }
    }

}
