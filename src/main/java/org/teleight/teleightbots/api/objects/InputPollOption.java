package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record InputPollOption(
        @JsonProperty(value = "text", required = true)
        @NotNull
        String text,

        @JsonProperty(value = "text_parse_mode")
        @Nullable
        ParseMode textParseMode,

        @JsonProperty(value = "text_entities")
        @Nullable
        MessageEntity[] textEntities
) implements ApiResult {

    public static @NotNull Builder ofBuilder(String text) {
        return new InputPollOption.Builder().text(text);
    }
}
