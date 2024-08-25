
package org.teleight.teleightbots.api.objects;

import org.jetbrains.annotations.NotNull;

public record BotCommandScopeAllPrivateChats() implements BotCommandScope {

    @Override
    public @NotNull String type() {
        return "all_private_chats";
    }

}
