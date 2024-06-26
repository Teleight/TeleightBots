
package org.teleight.teleightbots.api.objects;

import org.jetbrains.annotations.NotNull;

record BotCommandScopeAllPrivateChats() implements BotCommandScope {

    @Override
    public @NotNull BotCommandScope.BotCommandScopeType type() {
        return BotCommandScopeType.ALL_PRIVATE_CHATS;
    }

}
