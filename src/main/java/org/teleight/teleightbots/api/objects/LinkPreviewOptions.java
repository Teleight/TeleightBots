package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record LinkPreviewOptions(
        @JsonProperty(value = "is_disabled")
        boolean isDisabled,

        @JsonProperty(value = "url")
        @Nullable
        String url,

        @JsonProperty(value = "prefer_small_media")
        boolean preferSmallMedia,

        @JsonProperty(value = "prefer_large_media")
        boolean preferLargeMedia,

        @JsonProperty(value = "show_above_text")
        boolean showAboveText
) implements ApiResult {

    public static @NotNull Builder ofBuilder() {
        return new LinkPreviewOptions.Builder();
    }

    public static final class Builder {
        private boolean isDisabled;
        private String url;
        private boolean preferSmallMedia;
        private boolean preferLargeMedia;
        private boolean showAboveText;

        public Builder isDisabled(boolean isDisabled) {
            this.isDisabled = isDisabled;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder preferSmallMedia(boolean preferSmallMedia) {
            this.preferSmallMedia = preferSmallMedia;
            return this;
        }

        public Builder preferLargeMedia(boolean preferLargeMedia) {
            this.preferLargeMedia = preferLargeMedia;
            return this;
        }

        public Builder showAboveText(boolean showAboveText) {
            this.showAboveText = showAboveText;
            return this;
        }

        public LinkPreviewOptions build() {
            return new LinkPreviewOptions(this.isDisabled, this.url, this.preferSmallMedia, this.preferLargeMedia, this.showAboveText);
        }
    }

}
