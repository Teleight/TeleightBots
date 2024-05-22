package org.teleight.teleightbots.extensions;

import org.teleight.teleightbots.bot.TelegramBot;

/**
 * Blueprint for creating different types of extensions for the bot. Each extension has a reference to the bot
 */
public abstract class Extension {

    // A reference to the bot that this extension is associated with.
    protected final TelegramBot bot;

    /**
     * The constructor for the Extension class.
     *
     * @param bot The bot that this extension is associated with.
     */
    public Extension(TelegramBot bot) {
        this.bot = bot;
    }

    /**
     * Method used when the extension is started.
     */
    public abstract void start();

    /**
     * Method used when the extension is shut down.
     */
    public abstract void shutdown();

    /**
     * Returns the name of the extension.
     *
     * @return The name of the extension.
     */
    public abstract String getName();

}
