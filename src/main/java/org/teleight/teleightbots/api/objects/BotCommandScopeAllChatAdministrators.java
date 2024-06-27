package org.teleight.teleightbots.api.objects;

import org.jetbrains.annotations.NotNull;

public record BotCommandScopeAllChatAdministrators() implements BotCommandScope {

    @Override
    public @NotNull BotCommandScope.BotCommandScopeType type() {
        return BotCommandScopeType.ALL_CHAT_ADMINS;
    }

}
