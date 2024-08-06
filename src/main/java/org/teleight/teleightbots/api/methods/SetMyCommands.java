package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.BotCommand;
import org.teleight.teleightbots.api.objects.BotCommandScope;

import java.util.List;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record SetMyCommands(
        @JsonProperty(value = "commands", required = true)
        @NotNull
        List<BotCommand> commands,

        @JsonProperty(value = "scope")
        @Nullable
        BotCommandScope scope,

        @JsonProperty(value = "language_code")
        @Nullable
        String languageCode
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(List<BotCommand> commands) {
        return new SetMyCommands.Builder().commands(commands);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setMyCommands";
    }

}
