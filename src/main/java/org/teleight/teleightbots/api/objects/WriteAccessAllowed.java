package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record WriteAccessAllowed(
        @JsonProperty(value = "from_request")
        boolean fromRequest,

        @JsonProperty(value = "web_app_name")
        @Nullable
        String webAppName,

        @JsonProperty(value = "from_attachment_menu")
        boolean fromAttachmentMenu
) implements ApiResult {
}
