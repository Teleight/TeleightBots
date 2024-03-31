package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.methods.callback.CallbackQuery;
import org.teleight.teleightbots.api.objects.business.BusinessConnection;
import org.teleight.teleightbots.api.objects.business.BusinessMessagesDeleted;
import org.teleight.teleightbots.api.objects.chat.ChatJoinRequest;
import org.teleight.teleightbots.api.objects.chat.ChatMemberUpdated;
import org.teleight.teleightbots.api.objects.chat.boost.ChatBoostRemoved;
import org.teleight.teleightbots.api.objects.chat.boost.ChatBoostUpdated;
import org.teleight.teleightbots.api.objects.inline.ChosenInlineResult;
import org.teleight.teleightbots.api.objects.inline.InlineQuery;
import org.teleight.teleightbots.api.objects.payment.PreCheckoutQuery;
import org.teleight.teleightbots.api.objects.poll.Poll;
import org.teleight.teleightbots.api.objects.poll.PollAnswer;
import org.teleight.teleightbots.api.objects.reaction.MessageReactionCountUpdated;
import org.teleight.teleightbots.api.objects.reaction.MessageReactionUpdated;
import org.teleight.teleightbots.api.objects.shipping.ShippingQuery;

public record Update(
        @JsonProperty(value = "update_id", required = true)
        int updateId,

        @JsonProperty(value = "message")
        @Nullable
        Message message,

        @JsonProperty("edited_message")
        @Nullable
        Message editedMessage,

        @JsonProperty("channel_post")
        @Nullable
        Message channelPost,

        @JsonProperty("edited_channel_post")
        @Nullable
        Message editedChannelPost,

        @JsonProperty("business_connection")
        @Nullable
        BusinessConnection businessConnection,

        @JsonProperty("business_message")
        @Nullable
        Message businessMessage,

        @JsonProperty("edited_business_message")
        @Nullable
        Message editedBusinessMessage,

        @JsonProperty("deleted_business_messages")
        @Nullable
        BusinessMessagesDeleted deletedBusinessMessages,

        @JsonProperty("message_reaction")
        @Nullable
        MessageReactionUpdated messageReaction,

        @JsonProperty("message_reaction_count")
        @Nullable
        MessageReactionCountUpdated messageReactionCount,

        @JsonProperty("inline_query")
        @Nullable
        InlineQuery inlineQuery,

        @JsonProperty("chosen_inline_result")
        @Nullable
        ChosenInlineResult chosenInlineResult,

        @JsonProperty("callback_query")
        @Nullable
        CallbackQuery callbackQuery,

        @JsonProperty("shipping_query")
        @Nullable
        ShippingQuery shippingQuery,

        @JsonProperty("pre_checkout_query")
        @Nullable
        PreCheckoutQuery preCheckoutQuery,

        @JsonProperty("poll")
        @Nullable
        Poll poll,

        @JsonProperty("poll_answer")
        @Nullable
        PollAnswer pollAnswer,

        @JsonProperty("my_chat_member")
        @Nullable
        ChatMemberUpdated myChatMember,

        @JsonProperty("chat_member")
        @Nullable
        ChatMemberUpdated chatMember,

        @JsonProperty("chat_join_request")
        @Nullable
        ChatJoinRequest chatJoinRequest,

        @JsonProperty("chat_boost")
        @Nullable
        ChatBoostUpdated chatBoost,

        @JsonProperty("removed_chat_boost")
        @Nullable
        ChatBoostRemoved removedChatBoost
) implements ApiResult {

}
