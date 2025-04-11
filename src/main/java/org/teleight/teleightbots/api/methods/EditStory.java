package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.MultiPartApiMethod;
import org.teleight.teleightbots.api.objects.InputStoryContent;
import org.teleight.teleightbots.api.objects.MessageEntity;
import org.teleight.teleightbots.api.objects.ParseMode;
import org.teleight.teleightbots.api.objects.Story;
import org.teleight.teleightbots.api.objects.StoryArea;

import java.util.HashMap;
import java.util.Map;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record EditStory(
        @JsonProperty(value = "business_connection_id", required = true)
        @NotNull
        String businessConnectionId,

        @JsonProperty(value = "story_id", required = true)
        int storyId,

        @JsonProperty(value = "content", required = true)
        @NotNull
        InputStoryContent content,

        @JsonProperty(value = "caption")
        @Nullable
        String caption,

        @JsonProperty(value = "parse_mode")
        @Nullable
        ParseMode parseMode,

        @JsonProperty(value = "caption_entities")
        @Nullable
        MessageEntity[] captionEntities,

        @JsonProperty(value = "areas")
        @Nullable
        StoryArea[] areas
) implements MultiPartApiMethod<Story> {

    public static @NotNull Builder ofBuilder(String businessConnectionId, int storyId, InputStoryContent content) {
        return new EditStory.Builder()
                .businessConnectionId(businessConnectionId)
                .storyId(storyId)
                .content(content);
    }

    @Override
    public @NotNull Story deserializeResponse(@NotNull String answer) {
        return deserializeResponse(answer, Story.class);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editStory";
    }

    @Override
    public @NotNull Map<String, Object> getParameters() {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("business_connection_id", businessConnectionId);
        parameters.put("story_id", storyId);
        parameters.put("content", content);
        parameters.put("caption", caption);
        parameters.put("parse_mode", parseMode);
        parameters.put("caption_entities", captionEntities);
        parameters.put("areas", areas);
        return parameters;
    }
}
