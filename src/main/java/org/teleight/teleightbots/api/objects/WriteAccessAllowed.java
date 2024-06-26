package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record WriteAccessAllowed(
        @JsonProperty("from_request")
        boolean fromRequest,

        @JsonProperty("web_app_name")
        @Nullable
        String webAppName,

        @JsonProperty("from_attachment_menu")
        boolean fromAttachmentMenu
) implements ApiResult {
}
