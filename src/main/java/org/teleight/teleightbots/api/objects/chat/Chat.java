package org.teleight.teleightbots.api.objects.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.Message;

public record Chat(
        @JsonProperty(value = "id", required = true)
        Long id,

        @JsonProperty(value = "type", required = true)
        String type,

        @JsonProperty("title")
        @Nullable
        String title,

        @JsonProperty("username")
        @Nullable
        String username,

        @JsonProperty("first_name")
        @Nullable
        String firstName,

        @JsonProperty("last_name")
        @Nullable
        String lastName,

        @JsonProperty("is_forum")
        @Nullable
        Boolean isForum,

        @JsonProperty("photo")
        @Nullable
        ChatPhoto photo,

        @JsonProperty("active_usernames")
        @Nullable
        String[] activeUsernames,

        @JsonProperty("emoji_status_custom_emoji_id")
        @Nullable
        String emojiStatusCustomEmojiId,

        @JsonProperty("emoji_status_expiration_date")
        @Nullable
        Integer emojiStatusExpirationDate,

        @JsonProperty("bio")
        @Nullable
        String bio,

        @JsonProperty("has_private_forwards")
        @Nullable
        Boolean hasPrivateForwards,

        @JsonProperty("has_restricted_voice_and_video_messages")
        @Nullable
        Boolean hasRestrictedVoiceAndVideoMessages,

        @JsonProperty("join_to_send_messages")
        @Nullable
        Boolean joinToSendMessages,

        @JsonProperty("join_by_request")
        @Nullable
        Boolean joinByRequest,

        @JsonProperty("description")
        @Nullable
        String description,

        @JsonProperty("invite_link")
        @Nullable
        String inviteLink,

        @JsonProperty("pinned_message")
        @Nullable
        Message pinnedMessage,

        @JsonProperty("permissions")
        @Nullable
        ChatPermissions permissions,

        @JsonProperty("slow_mode_delay")
        @Nullable
        Integer slowModeDelay,

        @JsonProperty("message_auto_delete_time")
        @Nullable
        Integer messageAutoDeleteTime,

        @JsonProperty("has_aggressive_anti_spam_enabled")
        @Nullable
        Boolean hasAggressiveAntiSpamEnabled,

        @JsonProperty("has_hidden_members")
        @Nullable
        Boolean hasHiddenMembers,

        @JsonProperty("has_protected_content")
        @Nullable
        Boolean hasProtectedContent,

        @JsonProperty("sticker_set_name")
        @Nullable
        String stickerSetName,

        @JsonProperty("can_set_sticker_set")
        @Nullable
        Boolean canSetStickerSet,

        @JsonProperty("linked_chat_id")
        @Nullable
        Long linkedChatId,

        @JsonProperty("location")
        @Nullable
        ChatLocation location
) implements ApiResult {
}
