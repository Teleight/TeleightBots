package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record DeleteStory(
        @JsonProperty(value = "business_connection_id", required = true)
        @NotNull
        String businessConnectionId,

        @JsonProperty(value = "story_id", required = true)
        int storyId
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String businessConnectionId, int storyId) {
        return new DeleteStory.Builder()
                .businessConnectionId(businessConnectionId)
                .storyId(storyId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "deleteStory";
    }
}
