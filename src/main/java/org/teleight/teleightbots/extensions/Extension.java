package org.teleight.teleightbots.extensions;

import org.teleight.teleightbots.bot.Bot;

public abstract class Extension {

    protected final Bot bot;

    public Extension(Bot bot) {
        this.bot = bot;
    }

    public abstract void start();

    public abstract void shutdown();

    public abstract String getName();

}
