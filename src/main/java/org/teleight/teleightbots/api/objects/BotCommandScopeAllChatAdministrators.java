package org.teleight.teleightbots.api.objects;

import org.jetbrains.annotations.NotNull;

public record BotCommandScopeAllChatAdministrators() implements BotCommandScope {

    @Override
    public @NotNull String type() {
        return "all_chat_administrators";
    }

}
