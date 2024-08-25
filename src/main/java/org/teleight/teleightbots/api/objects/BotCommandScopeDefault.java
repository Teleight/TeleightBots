package org.teleight.teleightbots.api.objects;

import org.jetbrains.annotations.NotNull;

public record BotCommandScopeDefault() implements BotCommandScope {

    @Override
    public @NotNull String type() {
        return "default";
    }

}
