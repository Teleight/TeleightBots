package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record MessageEntity(
        @JsonProperty(value = "type", required = true)
        String type,

        @JsonProperty(value = "offset", required = true)
        int offset,

        @JsonProperty(value = "length", required = true)
        int length,

        @JsonProperty("url")
        @Nullable
        String url,

        @JsonProperty("user")
        @Nullable
        User user,

        @JsonProperty("language")
        @Nullable
        String language,

        @JsonProperty("custom_emoji_id")
        @Nullable
        String customEmojiId
) implements ApiResult {

    public static @NotNull Builder ofBuilder(String type, int offset, int length) {
        return new MessageEntity.Builder().type(type).offset(offset).length(length);
    }

}
