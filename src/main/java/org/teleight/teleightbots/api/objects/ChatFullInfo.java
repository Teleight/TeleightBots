package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

import java.util.Date;

public record ChatFullInfo(
        @JsonProperty(value = "id", required = true)
        String id,

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
        boolean isForum,

        @JsonProperty("accent_color_id")
        int accentColorId,

        @JsonProperty("max_reaction_count")
        int maxReactionCount,

        @JsonProperty("photo")
        @Nullable
        ChatPhoto photo,

        @JsonProperty("active_usernames")
        @Nullable
        String[] activeUsernames,

        @JsonProperty("birthdate")
        @Nullable
        Birthdate birthdate,

        @JsonProperty("business_intro")
        @Nullable
        String businessIntro,

        @JsonProperty("business_location")
        @Nullable
        BusinessLocation businessLocation,

        @JsonProperty("business_opening_hours")
        @Nullable
        BusinessOpeningHours businessOpeningHours,

        @JsonProperty("personal_chat")
        @Nullable
        Chat personalChat,

        @JsonProperty("available_reactions")
        @Nullable
        ReactionType[] availableReactions,

        @JsonProperty("background_custom_emoji_id")
        @Nullable
        String backgroundCustomEmojiId,

        @JsonProperty("profile_accent_color_id")
        int profileAccentColorId,

        @JsonProperty("profile_background_custom_emoji_id")
        @Nullable
        String profileBackgroundCustomEmojiId,

        @JsonProperty("emoji_status_custom_emoji_id")
        @Nullable
        String emojiStatusCustomEmojiId,

        @JsonProperty("emoji_status_expiration_date")
        @Nullable
        Date emojiStatusExpirationDate,

        @JsonProperty("bio")
        @Nullable
        String bio,

        @JsonProperty("has_private_forwards")
        boolean hasPrivateForwards,

        @JsonProperty("has_restricted_voice_and_video_messages")
        boolean hasRestrictedVoiceAndVideoMessages,

        @JsonProperty("join_to_send_messages")
        boolean joinToSendMessages,

        @JsonProperty("join_by_request")
        boolean joinByRequest,

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
        int slowModeDelay,

        @JsonProperty("unrestrict_boost_count")
        int unrestrictBoostCount,

        @JsonProperty("message_auto_delete_time")
        int messageAutoDeleteTime,

        @JsonProperty("has_aggressive_anti_spam_enabled")
        boolean hasAggressiveAntiSpamEnabled,

        @JsonProperty("has_hidden_members")
        boolean hasHiddenMembers,

        @JsonProperty("has_protected_content")
        boolean hasProtectedContent,

        @JsonProperty("has_visible_history")
        boolean hasVisibleHistory,

        @JsonProperty("sticker_set_name")
        @Nullable
        String stickerSetName,

        @JsonProperty("can_set_sticker_set")
        boolean canSetStickerSet,

        @JsonProperty("custom_emoji_sticker_set_name")
        @Nullable
        String customEmojiStickerSetName,

        @JsonProperty("linked_chat_id")
        @Nullable
        Long linkedChatId,

        @JsonProperty("location")
        @Nullable
        ChatLocation location
) implements ApiResult {
}
