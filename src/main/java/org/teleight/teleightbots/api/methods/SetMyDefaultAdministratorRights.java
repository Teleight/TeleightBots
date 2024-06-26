package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;
import org.teleight.teleightbots.api.objects.ChatAdministratorRights;

public record SetMyDefaultAdministratorRights(
        @JsonProperty(value = "rights")
        @Nullable
        ChatAdministratorRights rights,

        @JsonProperty(value = "for_channels")
        boolean forChannels
) implements ApiMethodBoolean {

    public static Builder ofBuilder() {
        return new SetMyDefaultAdministratorRights.Builder();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "setMyDefaultAdministratorRights";
    }

    public static class Builder {
        private ChatAdministratorRights rights;
        private boolean forChannels;

        public Builder rights(ChatAdministratorRights rights) {
            this.rights = rights;
            return this;
        }

        public Builder forChannels(boolean forChannels) {
            this.forChannels = forChannels;
            return this;
        }

        public SetMyDefaultAdministratorRights build() {
            return new SetMyDefaultAdministratorRights(this.rights, this.forChannels);
        }
    }
}
