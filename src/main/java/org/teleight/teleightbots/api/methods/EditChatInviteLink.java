package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethodBoolean;

import java.util.Date;

public record EditChatInviteLink(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

        @JsonProperty(value = "invite_link", required = true)
        @NotNull
        String inviteLink,

        @JsonProperty(value = "name")
        @Nullable
        String name,

        @JsonProperty(value = "expire_date")
        @Nullable
        Date expireDate,

        @JsonProperty(value = "member_limit")
        int memberLimit,

        @JsonProperty(value = "creates_join_request")
        boolean createsJoinRequest
) implements ApiMethodBoolean {

    public static Builder of(String chatId, String inviteLink) {
        return new Builder(chatId, inviteLink);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "editChatInviteLink";
    }

    public static class Builder {
        private final String chatId;
        private final String inviteLink;
        private String name;
        private Date expireDate;
        private int memberLimit;
        private boolean createsJoinRequest;

        Builder(String chatId, String inviteLink) {
            this.chatId = chatId;
            this.inviteLink = inviteLink;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder expireDate(Date expireDate) {
            this.expireDate = expireDate;
            return this;
        }

        public Builder memberLimit(int memberLimit) {
            this.memberLimit = memberLimit;
            return this;
        }

        public Builder createsJoinRequest(boolean createsJoinRequest) {
            this.createsJoinRequest = createsJoinRequest;
            return this;
        }

        public EditChatInviteLink build() {
            return new EditChatInviteLink(this.chatId, this.inviteLink, this.name, this.expireDate, this.memberLimit, this.createsJoinRequest);
        }
    }
}
