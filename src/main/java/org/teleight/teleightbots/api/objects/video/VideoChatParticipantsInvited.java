package org.teleight.teleightbots.api.objects.video;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;

public record VideoChatParticipantsInvited(
        @JsonProperty(value = "users", required = true)
        User[] users
) implements ApiResult {
}
