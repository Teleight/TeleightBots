package org.teleight.teleightbots.api.objects;

import org.jetbrains.annotations.NotNull;

record BotCommandScopeDefault() implements BotCommandScope {

    @Override
    public @NotNull BotCommandScope.BotCommandScopeType type() {
        return BotCommandScopeType.DEFAULT;
    }

}
