package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.inline.result.InlineQueryResult;

public record AnswerInlineQuery(
        @JsonProperty(value = "inline_query_id", required = true)
        String inlineQueryId,

        @JsonProperty(value = "results", required = true)
        InlineQueryResult[] results,

        @JsonProperty(value = "cache_time")
        int cacheTime,

        @JsonProperty(value = "is_personal")
        boolean isPersonal
) implements ApiMethodBoolean {

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "AnswerInlineQuery";
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder inlineQueryId(@NotNull String inlineQueryId);

        @NotNull Builder results(@NotNull InlineQueryResult... results);

        @NotNull Builder cacheTime(int cacheTime);

        @NotNull Builder isPersonal(boolean isPersonal);

        @NotNull AnswerInlineQuery build();
    }

    static final class BuilderImpl implements Builder {

        private String inlineQueryId;
        private InlineQueryResult[] results;
        private int cacheTime;
        private boolean isPersonal;

        @Override
        public @NotNull Builder inlineQueryId(@NotNull String inlineQueryId) {
            this.inlineQueryId = inlineQueryId;
            return this;
        }

        @Override
        public @NotNull Builder results(@NotNull InlineQueryResult... results) {
            this.results = results;
            return this;
        }

        @Override
        public @NotNull Builder cacheTime(int cacheTime) {
            this.cacheTime = cacheTime;
            return this;
        }

        @Override
        public @NotNull Builder isPersonal(boolean isPersonal) {
            this.isPersonal = isPersonal;
            return this;
        }

        @Override
        public @NotNull AnswerInlineQuery build() {
            return new AnswerInlineQuery(inlineQueryId, results, cacheTime, isPersonal);
        }
    }

}
