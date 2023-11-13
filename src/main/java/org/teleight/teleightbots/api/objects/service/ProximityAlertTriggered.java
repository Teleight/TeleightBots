package org.teleight.teleightbots.api.objects.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.User;

public record ProximityAlertTriggered(
        @JsonProperty(value = "traveler", required = true)
        User traveler,

        @JsonProperty(value = "watcher", required = true)
        User watcher,

        @JsonProperty(value = "distance", required = true)
        Integer distance
) implements ApiResult {
}
