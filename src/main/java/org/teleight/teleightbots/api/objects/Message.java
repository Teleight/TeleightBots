package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public record Message(
        @JsonProperty(value = "message_id", required = true)
        int messageId,

        @JsonProperty("message_thread_id")
        int messageThreadId,

        @JsonProperty("from")
        @Nullable
        User from,

        @JsonProperty("sender_chat")
        @Nullable
        Chat senderChat,

        @JsonProperty("sender_boost_count")
        int senderBoostCount,

        @JsonProperty("sender_business_bot")
        @Nullable
        User senderBusinessBot,

        @JsonProperty(value = "date", required = true)
        @NotNull
        Date date,

        @JsonProperty("business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat", required = true)
        @NotNull
        Chat chat,

        @JsonProperty("forward_origin")
        @Nullable
        MessageOrigin forwardOrigin,

        @JsonProperty("is_topic_message")
        boolean isTopicMessage,

        @JsonProperty("is_automatic_forward")
        boolean isAutomaticForward,

        @JsonProperty("reply_to_message")
        @Nullable
        Message replyToMessage,

        @JsonProperty("external_reply")
        @Nullable
        ExternalReplyInfo externalReply,

        @JsonProperty("quote")
        @Nullable
        TextQuote quote,

        @JsonProperty("reply_to_story")
        @Nullable
        Story replyToStory,

        @JsonProperty("via_bot")
        @Nullable
        User viaBot,

        @JsonProperty("edit_date")
        @Nullable
        Date editDate,

        @JsonProperty("has_protected_content")
        boolean hasProtectedContent,

        @JsonProperty("is_from_offline")
        boolean isFromOffline,

        @JsonProperty("media_group_id")
        @Nullable
        String mediaGroupId,

        @JsonProperty("author_signature")
        @Nullable
        String authorSignature,

        @JsonProperty("text")
        @Nullable
        String text,

        @JsonProperty("entities")
        @Nullable
        MessageEntity[] entities,

        @JsonProperty("link_preview_options")
        @Nullable
        LinkPreviewOptions linkPreviewOptions,

        @JsonProperty("animation")
        @Nullable
        Animation animation,

        @JsonProperty("audio")
        @Nullable
        Audio audio,

        @JsonProperty("document")
        @Nullable
        Document document,

        @JsonProperty("photo")
        @Nullable
        PhotoSize[] photo,

        @JsonProperty("sticker")
        @Nullable
        Sticker sticker,

        @JsonProperty("story")
        @Nullable
        Story story,

        @JsonProperty("video")
        @Nullable
        Video video,

        @JsonProperty("video_note")
        @Nullable
        VideoNote videoNote,

        @JsonProperty("voice")
        @Nullable
        Voice voice,

        @JsonProperty("caption")
        @Nullable
        String caption,

        @JsonProperty("caption_entities")
        @Nullable
        MessageEntity[] captionEntities,

        @JsonProperty("has_media_spoiler")
        boolean hasMediaSpoiler,

        @JsonProperty("contact")
        @Nullable
        Contact contact,

        @JsonProperty("dice")
        @Nullable
        Dice dice,

        @JsonProperty("game")
        @Nullable
        Game game,

        @JsonProperty("poll")
        @Nullable
        Poll poll,

        @JsonProperty("venue")
        @Nullable
        Venue venue,

        @JsonProperty("location")
        @Nullable
        Location location,

        @JsonProperty("new_chat_members")
        @Nullable
        User[] newChatMembers,

        @JsonProperty("left_chat_member")
        @Nullable
        User leftChatMember,

        @JsonProperty("new_chat_title")
        @Nullable
        String newChatTitle,

        @JsonProperty("new_chat_photo")
        @Nullable
        PhotoSize[] newChatPhoto,

        @JsonProperty("delete_chat_photo")
        boolean deleteChatPhoto,

        @JsonProperty("group_chat_created")
        boolean groupChatCreated,

        @JsonProperty("supergroup_chat_created")
        boolean supergroupChatCreated,

        @JsonProperty("channel_chat_created")
        boolean channelChatCreated,

        @JsonProperty("message_auto_delete_timer_changed")
        @Nullable
        MessageAutoDeleteTimerChanged messageAutoDeleteTimerChanged,

        @JsonProperty("migrate_to_chat_id")
        @Nullable
        Long migrateToChatId,

        @JsonProperty("migrate_from_chat_id")
        @Nullable
        Long migrateFromChatId,

        @JsonProperty("pinned_message")
        @Nullable
        MaybeInaccessibleMessage pinnedMessage,

        @JsonProperty("invoice")
        @Nullable
        Invoice invoice,

        @JsonProperty("successful_payment")
        @Nullable
        SuccessfulPayment successfulPayment,

        @JsonProperty("users_shared")
        @Nullable
        UsersShared userShared,

        @JsonProperty("chat_shared")
        @Nullable
        ChatShared chatShared,

        @JsonProperty("connected_website")
        @Nullable
        String connectedWebsite,

        @JsonProperty("write_access_allowed")
        @Nullable
        WriteAccessAllowed writeAccessAllowed,

        @JsonProperty("passport_data")
        @Nullable
        PassportData passportData,

        @JsonProperty("proximity_alert_triggered")
        @Nullable
        ProximityAlertTriggered proximityAlertTriggered,

        @JsonProperty("boost_added")
        @Nullable
        ChatBoostAdded boostAdded,

        @JsonProperty("chat_background_set")
        @Nullable
        ChatBackground chatBackgroundSet,

        @JsonProperty("forum_topic_created")
        @Nullable
        ForumTopicCreated forumTopicCreated,

        @JsonProperty("forum_topic_edited")
        @Nullable
        ForumTopicEdited forumTopicEdited,

        @JsonProperty("forum_topic_closed")
        @Nullable
        ForumTopicClosed forumTopicClosed,

        @JsonProperty("forum_topic_reopened")
        @Nullable
        ForumTopicReopened forumTopicReopened,

        @JsonProperty("general_forum_topic_hidden")
        @Nullable
        GeneralForumTopicHidden generalForumTopicHidden,

        @JsonProperty("general_forum_topic_unhidden")
        @Nullable
        GeneralForumTopicUnhidden generalForumTopicUnhidden,

        @JsonProperty("giveaway_created")
        @Nullable
        GiveawayCreated giveawayCreated,

        @JsonProperty("giveaway")
        @Nullable
        Giveaway giveaway,

        @JsonProperty("giveaway_winners")
        @Nullable
        GiveawayWinners giveawayWinners,

        @JsonProperty("giveaway_completed")
        @Nullable
        GiveawayCompleted giveawayCompleted,

        @JsonProperty("video_chat_scheduled")
        @Nullable
        VideoChatScheduled videoChatScheduled,

        @JsonProperty("video_chat_started")
        @Nullable
        VideoChatStarted videoChatStarted,

        @JsonProperty("video_chat_ended")
        @Nullable
        VideoChatEnded videoChatEnded,

        @JsonProperty("video_chat_participants_invited")
        @Nullable
        VideoChatParticipantsInvited videoChatParticipantsInvited,

        @JsonProperty("web_app_data")
        @Nullable
        WebAppData webAppData,

        @JsonProperty("reply_markup")
        @Nullable
        ReplyKeyboard replyMarkup
) implements MaybeInaccessibleMessage {

    @JsonIgnore
    public String chatId() {
        return chat.id();
    }

    @JsonIgnore
    public boolean isGroup() {
        return chat.isGroup();
    }

    @JsonIgnore
    public boolean isChannel() {
        return chat.isChannel();
    }

    @JsonIgnore
    public boolean isUser() {
        return chat.isUser();
    }

    @JsonIgnore
    public boolean isSuperGroup() {
        return chat.isSuperGroup();
    }

}
