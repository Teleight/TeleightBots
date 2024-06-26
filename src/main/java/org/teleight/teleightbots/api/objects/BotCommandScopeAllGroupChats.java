package org.teleight.teleightbots.api.objects;

import org.jetbrains.annotations.NotNull;

record BotCommandScopeAllGroupChats() implements BotCommandScope {

    @Override
    public @NotNull BotCommandScope.BotCommandScopeType type() {
        return BotCommandScopeType.ALL_GROUP_CHATS;
    }

}
