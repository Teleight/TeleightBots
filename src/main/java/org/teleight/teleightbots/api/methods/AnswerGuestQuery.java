package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.InlineQueryResult;
import org.teleight.teleightbots.api.objects.SentGuestMessage;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record AnswerGuestQuery(
        @JsonProperty(value = "guest_query_id", required = true)
        @NotNull
        String guestQueryId,

        @JsonProperty(value = "result", required = true)
        @NotNull
        InlineQueryResult result
) implements ApiMethod<SentGuestMessage> {

    public static @NotNull Builder ofBuilder(String guestQueryId, InlineQueryResult result) {
        return new AnswerGuestQuery.Builder()
                .guestQueryId(guestQueryId)
                .result(result);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "answerGuestQuery";
    }

    @Override
    public @NotNull SentGuestMessage deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, SentGuestMessage.class);
    }

}
