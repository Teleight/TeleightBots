package org.teleight.teleightbots.api.objects;

import org.jetbrains.annotations.NotNull;

public record BotCommandScopeAllGroupChats() implements BotCommandScope {

    @Override
    public @NotNull String type() {
        return "all_group_chats";
    }

}
