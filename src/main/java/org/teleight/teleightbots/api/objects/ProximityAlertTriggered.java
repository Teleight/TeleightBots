package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;

public record ProximityAlertTriggered(
        @JsonProperty(value = "traveler", required = true)
        User traveler,

        @JsonProperty(value = "watcher", required = true)
        User watcher,

        @JsonProperty(value = "distance", required = true)
        int distance
) implements ApiResult {
}
