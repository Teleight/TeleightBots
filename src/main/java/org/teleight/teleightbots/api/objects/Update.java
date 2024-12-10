package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record Update(
        @JsonProperty(value = "update_id", required = true)
        int updateId,

        @JsonProperty(value = "message")
        @Nullable
        Message message,

        @JsonProperty(value = "edited_message")
        @Nullable
        Message editedMessage,

        @JsonProperty(value = "channel_post")
        @Nullable
        Message channelPost,

        @JsonProperty(value = "edited_channel_post")
        @Nullable
        Message editedChannelPost,

        @JsonProperty(value = "business_connection")
        @Nullable
        BusinessConnection businessConnection,

        @JsonProperty(value = "business_message")
        @Nullable
        Message businessMessage,

        @JsonProperty(value = "edited_business_message")
        @Nullable
        Message editedBusinessMessage,

        @JsonProperty(value = "deleted_business_messages")
        @Nullable
        BusinessMessagesDeleted deletedBusinessMessages,

        @JsonProperty(value = "message_reaction")
        @Nullable
        MessageReactionUpdated messageReaction,

        @JsonProperty(value = "message_reaction_count")
        @Nullable
        MessageReactionCountUpdated messageReactionCount,

        @JsonProperty(value = "inline_query")
        @Nullable
        InlineQuery inlineQuery,

        @JsonProperty(value = "chosen_inline_result")
        @Nullable
        ChosenInlineResult chosenInlineResult,

        @JsonProperty(value = "callback_query")
        @Nullable
        CallbackQuery callbackQuery,

        @JsonProperty(value = "shipping_query")
        @Nullable
        ShippingQuery shippingQuery,

        @JsonProperty(value = "pre_checkout_query")
        @Nullable
        PreCheckoutQuery preCheckoutQuery,

        @JsonProperty(value = "purchased_paid_media")
        @Nullable
        PaidMediaPurchased purchasedPaidMedia,

        @JsonProperty(value = "poll")
        @Nullable
        Poll poll,

        @JsonProperty(value = "poll_answer")
        @Nullable
        PollAnswer pollAnswer,

        @JsonProperty(value = "my_chat_member")
        @Nullable
        ChatMemberUpdated myChatMember,

        @JsonProperty(value = "chat_member")
        @Nullable
        ChatMemberUpdated chatMember,

        @JsonProperty(value = "chat_join_request")
        @Nullable
        ChatJoinRequest chatJoinRequest,

        @JsonProperty(value = "chat_boost")
        @Nullable
        ChatBoostUpdated chatBoost,

        @JsonProperty(value = "removed_chat_boost")
        @Nullable
        ChatBoostRemoved removedChatBoost
) implements ApiResult {

}
