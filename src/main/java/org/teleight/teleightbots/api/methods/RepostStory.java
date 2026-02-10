package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.MultiPartApiMethod;
import org.teleight.teleightbots.api.objects.InputStoryContent;
import org.teleight.teleightbots.api.objects.MessageEntity;
import org.teleight.teleightbots.api.objects.ParseMode;
import org.teleight.teleightbots.api.objects.Story;
import org.teleight.teleightbots.api.objects.StoryActivePeriod;
import org.teleight.teleightbots.api.objects.StoryArea;

import java.util.HashMap;
import java.util.Map;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record RepostStory(
        @JsonProperty(value = "business_connection_id", required = true)
        @NotNull
        String businessConnectionId,

        @JsonProperty(value = "from_chat_id", required = true)
        long fromChatId,

        @JsonProperty(value = "from_story_id", required = true)
        int fromStoryId,

        @JsonProperty(value = "active_period", required = true)
        StoryActivePeriod activePeriod,

        @JsonProperty(value = "post_to_chat_page")
        boolean postToChatPage,

        @JsonProperty(value = "protect_content")
        boolean protectContent
) implements ApiMethod<Story> {

    public static @NotNull Builder ofBuilder(String businessConnectionId, long fromChatId, int fromStoryId, StoryActivePeriod activePeriod) {
        return new RepostStory.Builder()
                .businessConnectionId(businessConnectionId)
                .fromChatId(fromChatId)
                .fromStoryId(fromStoryId)
                .activePeriod(activePeriod);
    }

    @Override
    public @NotNull Story deserializeResponse(@NotNull String answer) {
        return deserializeResponse(answer, Story.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "repostStory";
    }

}
