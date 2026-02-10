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

        @JsonProperty(value = "title")
        @Nullable
        String title,

        @JsonProperty(value = "username")
        @Nullable
        String username,

        @JsonProperty(value = "first_name")
        @Nullable
        String firstName,

        @JsonProperty(value = "last_name")
        @Nullable
        String lastName,

        @JsonProperty(value = "is_forum")
        boolean isForum,

        @JsonProperty(value = "is_direct_messages")
        boolean isDirectMessages,

        @JsonProperty(value = "accent_color_id")
        int accentColorId,

        @JsonProperty(value = "max_reaction_count")
        int maxReactionCount,

        @JsonProperty(value = "photo")
        @Nullable
        ChatPhoto photo,

        @JsonProperty(value = "active_usernames")
        @Nullable
        String[] activeUsernames,

        @JsonProperty(value = "birthdate")
        @Nullable
        Birthdate birthdate,

        @JsonProperty(value = "business_intro")
        @Nullable
        String businessIntro,

        @JsonProperty(value = "business_location")
        @Nullable
        BusinessLocation businessLocation,

        @JsonProperty(value = "business_opening_hours")
        @Nullable
        BusinessOpeningHours businessOpeningHours,

        @JsonProperty(value = "personal_chat")
        @Nullable
        Chat personalChat,

        @JsonProperty(value = "parent_chat")
        @Nullable
        Chat parentChat,

        @JsonProperty(value = "available_reactions")
        @Nullable
        ReactionType[] availableReactions,

        @JsonProperty(value = "background_custom_emoji_id")
        @Nullable
        String backgroundCustomEmojiId,

        @JsonProperty(value = "profile_accent_color_id")
        int profileAccentColorId,

        @JsonProperty(value = "profile_background_custom_emoji_id")
        @Nullable
        String profileBackgroundCustomEmojiId,

        @JsonProperty(value = "emoji_status_custom_emoji_id")
        @Nullable
        String emojiStatusCustomEmojiId,

        @JsonProperty(value = "emoji_status_expiration_date")
        @Nullable
        Date emojiStatusExpirationDate,

        @JsonProperty(value = "bio")
        @Nullable
        String bio,

        @JsonProperty(value = "has_private_forwards")
        boolean hasPrivateForwards,

        @JsonProperty(value = "has_restricted_voice_and_video_messages")
        boolean hasRestrictedVoiceAndVideoMessages,

        @JsonProperty(value = "join_to_send_messages")
        boolean joinToSendMessages,

        @JsonProperty(value = "join_by_request")
        boolean joinByRequest,

        @JsonProperty(value = "description")
        @Nullable
        String description,

        @JsonProperty(value = "invite_link")
        @Nullable
        String inviteLink,

        @JsonProperty(value = "pinned_message")
        @Nullable
        Message pinnedMessage,

        @JsonProperty(value = "permissions")
        @Nullable
        ChatPermissions permissions,

        @JsonProperty(value = "accepted_gift_types")
        @Nullable
        AcceptedGiftTypes acceptedGiftTypes,

        @JsonProperty(value = "can_send_paid_media")
        boolean canSendPaidMedia,

        @JsonProperty(value = "slow_mode_delay")
        int slowModeDelay,

        @JsonProperty(value = "unrestrict_boost_count")
        int unrestrictBoostCount,

        @JsonProperty(value = "message_auto_delete_time")
        int messageAutoDeleteTime,

        @JsonProperty(value = "has_aggressive_anti_spam_enabled")
        boolean hasAggressiveAntiSpamEnabled,

        @JsonProperty(value = "has_hidden_members")
        boolean hasHiddenMembers,

        @JsonProperty(value = "has_protected_content")
        boolean hasProtectedContent,

        @JsonProperty(value = "has_visible_history")
        boolean hasVisibleHistory,

        @JsonProperty(value = "sticker_set_name")
        @Nullable
        String stickerSetName,

        @JsonProperty(value = "can_set_sticker_set")
        boolean canSetStickerSet,

        @JsonProperty(value = "custom_emoji_sticker_set_name")
        @Nullable
        String customEmojiStickerSetName,

        @JsonProperty(value = "linked_chat_id")
        @Nullable
        Long linkedChatId,

        @JsonProperty(value = "location")
        @Nullable
        ChatLocation location,

        @JsonProperty(value = "rating")
        @Nullable
        UserRating rating,

        @JsonProperty(value = "first_profile_audio")
        @Nullable
        Audio firstProfileAudio,

        @JsonProperty(value = "unique_gift_colors")
        @Nullable
        UniqueGiftColors uniqueGiftColors,

        @JsonProperty(value = "paid_message_star_count")
        int paidMessageStarCount
) implements ApiResult {
}
