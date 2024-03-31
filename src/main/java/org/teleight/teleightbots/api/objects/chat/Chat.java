package org.teleight.teleightbots.api.objects.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.business.BusinessLocation;
import org.teleight.teleightbots.api.objects.business.BusinessOpeningHours;
import org.teleight.teleightbots.api.objects.reaction.ReactionType;
import org.teleight.teleightbots.api.objects.Birthdate;

import java.util.Date;

import static org.teleight.teleightbots.api.objects.chat.Chat.Type.*;

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

        @JsonProperty("accent_color_id")
        @Nullable
        Integer accentColorId,

        @JsonProperty("background_custom_emoji_id")
        @Nullable
        String backgroundCustomEmojiId,

        @JsonProperty("profile_accent_color_id")
        @Nullable
        Integer profileAccentColorId,

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

        @JsonProperty("unrestrict_boost_count")
        @Nullable
        Integer unrestrictBoostCount,

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

        @JsonProperty("has_visible_history")
        @Nullable
        Boolean hasVisibleHistory,

        @JsonProperty("sticker_set_name")
        @Nullable
        String stickerSetName,

        @JsonProperty("can_set_sticker_set")
        @Nullable
        Boolean canSetStickerSet,

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

        @JsonIgnore
        public boolean isGroup() {
                return GROUP_CHAT_TYPE.valueField.equals(type);
        }

        @JsonIgnore
        public boolean isChannel() {
                return CHANNEL_CHAT_TYPE.valueField.equals(type);
        }

        @JsonIgnore
        public boolean isUser() {
                return USER_CHAT_TYPE.valueField.equals(type);
        }

        @JsonIgnore
        public boolean isSuperGroup() {
                return SUPERGROUP_CHAT_TYPE.valueField.equals(type);
        }

        public enum Type {
                USER_CHAT_TYPE("private"),
                GROUP_CHAT_TYPE("group"),
                CHANNEL_CHAT_TYPE("channel"),
                SUPERGROUP_CHAT_TYPE("supergroup");

                private final String valueField;

                Type(String valueField) {
                        this.valueField = valueField;
                }
        }

}
