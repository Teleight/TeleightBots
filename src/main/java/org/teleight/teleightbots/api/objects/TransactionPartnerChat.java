package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record TransactionPartnerChat(
        @JsonProperty(value = "chat", required = true)
        @NotNull
        Chat chat,

        @JsonProperty(value = "gift")
        @Nullable
        Gift gift
) implements TransactionPartner {

    @Override
    public String type() {
        return "chat";
    }

}
