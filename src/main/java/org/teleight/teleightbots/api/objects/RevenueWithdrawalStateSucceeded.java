package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public record RevenueWithdrawalStateSucceeded(
        @JsonProperty(value = "date", required = true)
        @NotNull
        Date date,

        @JsonProperty(value = "url", required = true)
        @NotNull
        String url
) implements RevenueWithdrawalState {

    @Override
    public String type() {
        return "succeeded";
    }

}
