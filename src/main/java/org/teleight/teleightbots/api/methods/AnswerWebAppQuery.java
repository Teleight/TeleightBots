package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.InlineQueryResult;
import org.teleight.teleightbots.api.objects.SentWebAppMessage;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

@Builder(builderClassName = "Builder", toBuilder = true, builderMethodName = "ofBuilder")
@Jacksonized
public record AnswerWebAppQuery(
        @JsonProperty(value = "web_app_query_id", required = true)
        @NotNull
        String webAppQueryId,

        @JsonProperty(value = "result", required = true)
        @NotNull
        InlineQueryResult result
) implements ApiMethod<SentWebAppMessage> {

    public static @NotNull Builder ofBuilder(String webAppQueryId, InlineQueryResult result) {
        return new AnswerWebAppQuery.Builder().webAppQueryId(webAppQueryId).result(result);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "answerWebAppQuery";
    }

    @Override
    public @NotNull SentWebAppMessage deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, SentWebAppMessage.class);
    }

}
