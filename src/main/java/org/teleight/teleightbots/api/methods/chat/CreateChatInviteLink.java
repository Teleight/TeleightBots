package org.teleight.teleightbots.api.methods.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.chat.ChatInviteLink;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

public record CreateChatInviteLink(
        @JsonProperty(value = "chat_id", required = true)
        String chatId,

        @JsonProperty(value = "name")
        @Nullable
        String name,

        @JsonProperty(value = "expire_date")
        @Nullable
        Integer expireDate,

        @JsonProperty(value = "member_limit")
        @Nullable
        Integer memberLimit,

        @JsonProperty(value = "creates_join_request")
        @Nullable
        Boolean createsJoinRequest
) implements ApiMethod<ChatInviteLink> {

    public static @NotNull Builder builder() {
        return new BuilderImpl();
    }

    @Override
    public @NotNull String getEndpointURL() {
        return "createChatInviteLink";
    }

    public sealed interface Builder permits BuilderImpl {
        @NotNull Builder chatId(@NotNull String chatId);

        @NotNull Builder name(@Nullable String name);

        @NotNull Builder expireDate(int expireDate);

        @NotNull Builder memberLimit(int memberLimit);

        @NotNull Builder createsJoinRequest(boolean createsJoinRequest);

        @NotNull CreateChatInviteLink build();
    }

    static final class BuilderImpl implements Builder {
        private String chatId;
        private String name;
        private Integer expireDate;
        private Integer memberLimit;
        private Boolean createsJoinRequest;

        @Override
        public @NotNull Builder chatId(@NotNull String chatId) {
            this.chatId = chatId;
            return this;
        }

        @Override
        public @NotNull Builder name(@Nullable String name) {
            this.name = name;
            return this;
        }

        @Override
        public @NotNull Builder expireDate(int expireDate) {
            this.expireDate = expireDate;
            return this;
        }

        @Override
        public @NotNull Builder memberLimit(int memberLimit) {
            this.memberLimit = memberLimit;
            return this;
        }

        @Override
        public @NotNull Builder createsJoinRequest(boolean createsJoinRequest) {
            this.createsJoinRequest = createsJoinRequest;
            return this;
        }

        @Override
        public @NotNull CreateChatInviteLink build() {
            return new CreateChatInviteLink(chatId, name, expireDate, memberLimit, createsJoinRequest);
        }
    }

    @Override
    public @NotNull ChatInviteLink deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, ChatInviteLink.class);
    }

}
