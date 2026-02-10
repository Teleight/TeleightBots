package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public record Message(
        @JsonProperty(value = "message_id", required = true)
        int messageId,

        @JsonProperty(value = "message_thread_id")
        int messageThreadId,

        @JsonProperty(value = "direct_messages_topic")
        @Nullable
        DirectMessagesTopic directMessagesTopic,

        @JsonProperty(value = "from")
        @Nullable
        User from,

        @JsonProperty(value = "sender_chat")
        @Nullable
        Chat senderChat,

        @JsonProperty(value = "sender_boost_count")
        int senderBoostCount,

        @JsonProperty(value = "sender_business_bot")
        @Nullable
        User senderBusinessBot,

        @JsonProperty(value = "date", required = true)
        @NotNull
        Date date,

        @JsonProperty(value = "business_connection_id")
        @Nullable
        String businessConnectionId,

        @JsonProperty(value = "chat", required = true)
        @NotNull
        Chat chat,

        @JsonProperty(value = "forward_origin")
        @Nullable
        MessageOrigin forwardOrigin,

        @JsonProperty(value = "is_topic_message")
        boolean isTopicMessage,

        @JsonProperty(value = "is_automatic_forward")
        boolean isAutomaticForward,

        @JsonProperty(value = "reply_to_message")
        @Nullable
        Message replyToMessage,

        @JsonProperty(value = "external_reply")
        @Nullable
        ExternalReplyInfo externalReply,

        @JsonProperty(value = "quote")
        @Nullable
        TextQuote quote,

        @JsonProperty(value = "reply_to_story")
        @Nullable
        Story replyToStory,

        @JsonProperty(value = "reply_to_checklist_task_id")
        int replyToChecklistTaskId,

        @JsonProperty(value = "via_bot")
        @Nullable
        User viaBot,

        @JsonProperty(value = "edit_date")
        @Nullable
        Date editDate,

        @JsonProperty(value = "has_protected_content")
        boolean hasProtectedContent,

        @JsonProperty(value = "is_from_offline")
        boolean isFromOffline,

        @JsonProperty(value = "is_paid_post")
        boolean isPaidPost,

        @JsonProperty(value = "media_group_id")
        @Nullable
        String mediaGroupId,

        @JsonProperty(value = "author_signature")
        @Nullable
        String authorSignature,

        @JsonProperty(value = "paid_star_count")
        int paidStarCount,

        @JsonProperty(value = "text")
        @Nullable
        String text,

        @JsonProperty(value = "entities")
        @Nullable
        MessageEntity[] entities,

        @JsonProperty(value = "link_preview_options")
        @Nullable
        LinkPreviewOptions linkPreviewOptions,

        @JsonProperty(value = "effect_id")
        @Nullable
        String effectId,

        @JsonProperty(value = "animation")
        @Nullable
        Animation animation,

        @JsonProperty(value = "audio")
        @Nullable
        Audio audio,

        @JsonProperty(value = "document")
        @Nullable
        Document document,

        @JsonProperty(value = "paid_media")
        @Nullable
        PaidMedia paidMedia,

        @JsonProperty(value = "photo")
        @Nullable
        PhotoSize[] photo,

        @JsonProperty(value = "sticker")
        @Nullable
        Sticker sticker,

        @JsonProperty(value = "story")
        @Nullable
        Story story,

        @JsonProperty(value = "video")
        @Nullable
        Video video,

        @JsonProperty(value = "video_note")
        @Nullable
        VideoNote videoNote,

        @JsonProperty(value = "voice")
        @Nullable
        Voice voice,

        @JsonProperty(value = "caption")
        @Nullable
        String caption,

        @JsonProperty(value = "caption_entities")
        @Nullable
        MessageEntity[] captionEntities,

        @JsonProperty(value = "show_caption_above_media")
        boolean showCaptionAboveMedia,

        @JsonProperty(value = "has_media_spoiler")
        boolean hasMediaSpoiler,

        @JsonProperty(value = "checklist")
        @Nullable
        Checklist checklist,

        @JsonProperty(value = "contact")
        @Nullable
        Contact contact,

        @JsonProperty(value = "dice")
        @Nullable
        Dice dice,

        @JsonProperty(value = "game")
        @Nullable
        Game game,

        @JsonProperty(value = "poll")
        @Nullable
        Poll poll,

        @JsonProperty(value = "venue")
        @Nullable
        Venue venue,

        @JsonProperty(value = "location")
        @Nullable
        Location location,

        @JsonProperty(value = "new_chat_members")
        @Nullable
        User[] newChatMembers,

        @JsonProperty(value = "left_chat_member")
        @Nullable
        User leftChatMember,

        @JsonProperty(value = "chat_owner_left")
        @Nullable
        ChatOwnerLeft chatOwnerLeft,

        @JsonProperty(value = "chat_owner_changed")
        @Nullable
        ChatOwnerChanged chatOwnerChanged,

        @JsonProperty(value = "new_chat_title")
        @Nullable
        String newChatTitle,

        @JsonProperty(value = "new_chat_photo")
        @Nullable
        PhotoSize[] newChatPhoto,

        @JsonProperty(value = "delete_chat_photo")
        boolean deleteChatPhoto,

        @JsonProperty(value = "group_chat_created")
        boolean groupChatCreated,

        @JsonProperty(value = "supergroup_chat_created")
        boolean supergroupChatCreated,

        @JsonProperty(value = "channel_chat_created")
        boolean channelChatCreated,

        @JsonProperty(value = "message_auto_delete_timer_changed")
        @Nullable
        MessageAutoDeleteTimerChanged messageAutoDeleteTimerChanged,

        @JsonProperty(value = "migrate_to_chat_id")
        @Nullable
        Long migrateToChatId,

        @JsonProperty(value = "migrate_from_chat_id")
        @Nullable
        Long migrateFromChatId,

        @JsonProperty(value = "pinned_message")
        @Nullable
        MaybeInaccessibleMessage pinnedMessage,

        @JsonProperty(value = "invoice")
        @Nullable
        Invoice invoice,

        @JsonProperty(value = "successful_payment")
        @Nullable
        SuccessfulPayment successfulPayment,

        @JsonProperty(value = "refunded_payment")
        @Nullable
        RefundedPayment refundedPayment,

        @JsonProperty(value = "users_shared")
        @Nullable
        UsersShared userShared,

        @JsonProperty(value = "chat_shared")
        @Nullable
        ChatShared chatShared,

        @JsonProperty(value = "gift")
        @Nullable
        GiftInfo gift,

        @JsonProperty(value = "unique_gift")
        @Nullable
        UniqueGiftInfo uniqueGift,

        @JsonProperty(value = "gift_upgrade_sent")
        @Nullable
        GiftInfo giftUpgradeSent,

        @JsonProperty(value = "connected_website")
        @Nullable
        String connectedWebsite,

        @JsonProperty(value = "write_access_allowed")
        @Nullable
        WriteAccessAllowed writeAccessAllowed,

        @JsonProperty(value = "passport_data")
        @Nullable
        PassportData passportData,

        @JsonProperty(value = "proximity_alert_triggered")
        @Nullable
        ProximityAlertTriggered proximityAlertTriggered,

        @JsonProperty(value = "boost_added")
        @Nullable
        ChatBoostAdded boostAdded,

        @JsonProperty(value = "chat_background_set")
        @Nullable
        ChatBackground chatBackgroundSet,

        @JsonProperty(value = "checklist_tasks_done")
        @Nullable
        ChecklistTasksDone checklistTasksDone,

        @JsonProperty(value = "checklist_tasks_added")
        @Nullable
        ChecklistTasksAdded checklistTasksAdded,

        @JsonProperty(value = "direct_message_price_changed")
        @Nullable
        DirectMessagePriceChanged directMessagePriceChanged,

        @JsonProperty(value = "forum_topic_created")
        @Nullable
        ForumTopicCreated forumTopicCreated,

        @JsonProperty(value = "forum_topic_edited")
        @Nullable
        ForumTopicEdited forumTopicEdited,

        @JsonProperty(value = "forum_topic_closed")
        @Nullable
        ForumTopicClosed forumTopicClosed,

        @JsonProperty(value = "forum_topic_reopened")
        @Nullable
        ForumTopicReopened forumTopicReopened,

        @JsonProperty(value = "general_forum_topic_hidden")
        @Nullable
        GeneralForumTopicHidden generalForumTopicHidden,

        @JsonProperty(value = "general_forum_topic_unhidden")
        @Nullable
        GeneralForumTopicUnhidden generalForumTopicUnhidden,

        @JsonProperty(value = "giveaway_created")
        @Nullable
        GiveawayCreated giveawayCreated,

        @JsonProperty(value = "giveaway")
        @Nullable
        Giveaway giveaway,

        @JsonProperty(value = "giveaway_winners")
        @Nullable
        GiveawayWinners giveawayWinners,

        @JsonProperty(value = "giveaway_completed")
        @Nullable
        GiveawayCompleted giveawayCompleted,

        @JsonProperty(value = "paid_message_price_changed")
        @Nullable
        PaidMessagePriceChanged paidMessagePriceChanged,

        @JsonProperty(value = "suggested_post_approved")
        @Nullable
        SuggestedPostApproved suggestedPostApproved,

        @JsonProperty(value = "suggested_post_approval_failed")
        @Nullable
        SuggestedPostApprovalFailed suggestedPostApprovalFailed,

        @JsonProperty(value = "suggested_post_declined")
        @Nullable
        SuggestedPostDeclined suggestedPostDeclined,

        @JsonProperty(value = "suggested_post_paid")
        @Nullable
        SuggestedPostPaid suggestedPostPaid,

        @JsonProperty(value = "suggested_post_refunded")
        @Nullable
        SuggestedPostRefunded suggestedPostRefunded,

        @JsonProperty(value = "video_chat_scheduled")
        @Nullable
        VideoChatScheduled videoChatScheduled,

        @JsonProperty(value = "video_chat_started")
        @Nullable
        VideoChatStarted videoChatStarted,

        @JsonProperty(value = "video_chat_ended")
        @Nullable
        VideoChatEnded videoChatEnded,

        @JsonProperty(value = "video_chat_participants_invited")
        @Nullable
        VideoChatParticipantsInvited videoChatParticipantsInvited,

        @JsonProperty(value = "web_app_data")
        @Nullable
        WebAppData webAppData,

        @JsonProperty(value = "reply_markup")
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
