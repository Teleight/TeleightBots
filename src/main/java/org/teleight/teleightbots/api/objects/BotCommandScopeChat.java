package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public record BotCommandScopeChat(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId
) implements BotCommandScope {

    @Override
    public @NotNull BotCommandScope.BotCommandScopeType type() {
        return BotCommandScopeType.CHAT;
    }

}
