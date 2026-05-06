package org.teleight.teleightbots.extensions;

import org.teleight.teleightbots.bot.TelegramBot;

/// Blueprint for creating a new extension.
///
/// Each extension has a reference to its parent bot.
public abstract class Extension {

    /// A reference to the bot that this extension is associated with.
    protected final TelegramBot bot;

    /// The constructor for the Extension class.
    ///
    /// @param bot The bot that this extension is associated with.
    public Extension(TelegramBot bot) {
        this.bot = bot;
    }

    /// Method used when the extension is started.
    public void start() {

    }

    /// Method used when the extension is shut down.
    public void shutdown() {

    }

}
