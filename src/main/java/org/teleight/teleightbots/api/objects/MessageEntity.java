package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

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
        return new MessageEntity.Builder(type, offset, length);
    }

    public static final class Builder {
        private final String type;
        private final int offset;
        private final int length;
        private String url;
        private User user;
        private String language;
        private String customEmojiId;

        public Builder(String type, int offset, int length) {
            this.type = type;
            this.offset = offset;
            this.length = length;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Builder customEmojiId(String customEmojiId) {
            this.customEmojiId = customEmojiId;
            return this;
        }

        public MessageEntity build() {
            return new MessageEntity(this.type, this.offset, this.length, this.url, this.user, this.language, this.customEmojiId);
        }
    }
}
