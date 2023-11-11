package org.teleight.teleightbots.api.objects.poll;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public record PollOption(
        @JsonProperty(value = "text", required = true)
        String text,

        @JsonProperty(value = "voter_count", required = true)
        int voterCount
) implements ApiResult {

}
