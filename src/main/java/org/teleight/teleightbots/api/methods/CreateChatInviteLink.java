package org.teleight.teleightbots.api.methods;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.ChatInviteLink;

import java.util.Date;

public record CreateChatInviteLink(
        @JsonProperty(value = "chat_id", required = true)
        @NotNull
        String chatId,

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
) implements ApiMethod<ChatInviteLink> {

    public static Builder ofBuilder(String chatId) {
        return new CreateChatInviteLink.Builder(chatId);
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "createChatInviteLink";
    }

    public static class Builder {
        private final String chatId;
        private String name;
        private Date expireDate;
        private int memberLimit;
        private boolean createsJoinRequest;

        Builder(String chatId) {
            this.chatId = chatId;
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

        public CreateChatInviteLink build() {
            return new CreateChatInviteLink(this.chatId, this.name, this.expireDate, this.memberLimit, this.createsJoinRequest);
        }
    }

}
