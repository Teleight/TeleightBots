package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record InlineQueryResultPhoto(
        @JsonProperty(value = "id", required = true)
        String id,

        @JsonProperty(value = "photo_url", required = true)
        String photoUrl,

        @JsonProperty(value = "thumbnail_url", required = true)
        String thumbnailUrl,

        @JsonProperty(value = "caption")
        @Nullable
        String caption,

        @JsonProperty(value = "parse_mode")
        @Nullable
        ParseMode parseMode
) implements InlineQueryResult {

    public static @NotNull Builder of(String id, String photoUrl, String thumbnailUrl) {
        return new InlineQueryResultPhoto.Builder(id, photoUrl, thumbnailUrl);
    }

    @Override
    public String type() {
        return "photo";
    }

    public static final class Builder {
        private final String id;
        private final String photoUrl;
        private final String thumbnailUrl;
        private String caption;
        private ParseMode parseMode;

        public Builder(String id, String photoUrl, String thumbnailUrl) {
            this.id = id;
            this.photoUrl = photoUrl;
            this.thumbnailUrl = thumbnailUrl;
        }

        public @NotNull Builder caption(@NotNull String caption) {
            this.caption = caption;
            return this;
        }

        public @NotNull Builder parseMode(@NotNull ParseMode parseMode) {
            this.parseMode = parseMode;
            return this;
        }

        public @NotNull InlineQueryResultPhoto build() {
            return new InlineQueryResultPhoto(this.id, this.photoUrl, this.thumbnailUrl, this.caption, this.parseMode);
        }
    }

}
