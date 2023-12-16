package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.api.objects.entities.MessageEntity;
import org.teleight.teleightbots.api.objects.forum.*;
import org.teleight.teleightbots.api.objects.keyboard.InlineKeyboardMarkup;
import org.teleight.teleightbots.api.objects.passport.PassportData;
import org.teleight.teleightbots.api.objects.payment.Invoice;
import org.teleight.teleightbots.api.objects.payment.SuccessfulPayment;
import org.teleight.teleightbots.api.objects.poll.Poll;
import org.teleight.teleightbots.api.objects.service.*;
import org.teleight.teleightbots.api.objects.video.VideoChatEnded;
import org.teleight.teleightbots.api.objects.video.VideoChatParticipantsInvited;
import org.teleight.teleightbots.api.objects.video.VideoChatScheduled;
import org.teleight.teleightbots.api.objects.video.VideoChatStarted;

public record Message(
        @JsonProperty(value = "message_id", required = true)
        Integer messageId,

        @JsonProperty("message_thread_id")
        @Nullable
        Integer messageThreadId,

        @JsonProperty("from")
        @Nullable
        User from,

        @JsonProperty("sender_chat")
        @Nullable
        Chat senderChat,

        @JsonProperty(value = "date", required = true)
        Integer date,

        @JsonProperty(value = "chat", required = true)
        Chat chat,

        @JsonProperty("forward_from")
        @Nullable
        User forwardFrom,

        @JsonProperty("forward_from_chat")
        @Nullable
        Chat forwardFromChat,

        @JsonProperty("forward_from_message_id")
        @Nullable
        Integer forwardFromMessageId,

        @JsonProperty("forward_signature")
        @Nullable
        String forwardSignature,

        @JsonProperty("forward_sender_name")
        @Nullable
        String forwardSenderName,

        @JsonProperty("forward_date")
        @Nullable
        Integer forwardDate,

        @JsonProperty("is_topic_message")
        @Nullable
        Boolean isTopicMessage,

        @JsonProperty("is_automatic_forward")
        @Nullable
        Boolean isAutomaticForward,

        @JsonProperty("reply_to_message")
        @Nullable
        Message replyToMessage,

        @JsonProperty("via_bot")
        @Nullable
        User viaBot,

        @JsonProperty("edit_date")
        @Nullable
        Integer editDate,

        @JsonProperty("has_protected_content")
        @Nullable
        Boolean hasProtectedContent,

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
        @Nullable
        Boolean hasMediaSpoiler,

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
        @Nullable
        Boolean deleteChatPhoto,

        @JsonProperty("group_chat_created")
        @Nullable
        Boolean groupChatCreated,

        @JsonProperty("supergroup_chat_created")
        @Nullable
        Boolean supergroupChatCreated,

        @JsonProperty("channel_chat_created")
        @Nullable
        Boolean channelChatCreated,

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
        Message pinnedMessage,

        @JsonProperty("invoice")
        @Nullable
        Invoice invoice,

        @JsonProperty("successful_payment")
        @Nullable
        SuccessfulPayment successfulPayment,

        @JsonProperty("user_shared")
        @Nullable
        UserShared userShared,

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
        InlineKeyboardMarkup replyMarkup
) implements ApiResult {

        @JsonIgnore
        public Long getChatId() {
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
