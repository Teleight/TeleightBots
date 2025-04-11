package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record DeleteBusinessMessages(
        @JsonProperty(value = "business_connection_id", required = true)
        @NotNull
        String businessConnectionId,

        @JsonProperty(value = "message_ids", required = true)
        int[] messageIds
) implements ApiMethodBoolean {

    public static @NotNull Builder ofBuilder(String businessConnectionId, int[] messageIds) {
        return new DeleteBusinessMessages.Builder()
                .businessConnectionId(businessConnectionId)
                .messageIds(messageIds);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "deleteBusinessMessages";
    }
}
