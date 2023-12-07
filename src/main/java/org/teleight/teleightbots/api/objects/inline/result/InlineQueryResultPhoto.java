package org.teleight.teleightbots.api.objects.inline.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.utils.ParseMode;

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

        public static @NotNull Builder builder() {
                return new BuilderImpl();
        }

        public sealed interface Builder permits BuilderImpl {
                @NotNull Builder id(@NotNull String id);

                @NotNull Builder photoUrl(@NotNull String photoUrl);

                @NotNull Builder thumbnailUrl(@NotNull String thumbnailUrl);

                @NotNull Builder caption(@NotNull String caption);

                @NotNull Builder parseMode(@NotNull ParseMode parseMode);

                @NotNull InlineQueryResultPhoto build();
        }

        static final class BuilderImpl implements Builder {

                private String id;
                private String photoUrl;
                private String thumbnailUrl;
                private String caption;
                private ParseMode parseMode;

                @Override
                public @NotNull Builder id(@NotNull String id) {
                        this.id = id;
                        return this;
                }

                @Override
                public @NotNull Builder photoUrl(@NotNull String photoUrl) {
                        this.photoUrl = photoUrl;
                        return this;
                }

                @Override
                public @NotNull Builder thumbnailUrl(@NotNull String thumbnailUrl) {
                        this.thumbnailUrl = thumbnailUrl;
                        return this;
                }

                @Override
                public @NotNull Builder caption(@NotNull String caption) {
                        this.caption = caption;
                        return this;
                }

                @Override
                public @NotNull Builder parseMode(@NotNull ParseMode parseMode) {
                        this.parseMode = parseMode;
                        return this;
                }

                @Override
                public @NotNull InlineQueryResultPhoto build() {
                        return new InlineQueryResultPhoto(id, photoUrl, thumbnailUrl, caption, parseMode);
                }
        }

}
