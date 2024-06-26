package org.teleight.teleightbots.api.objects;

import org.teleight.teleightbots.api.ApiResult;

public sealed interface ReplyKeyboard extends ApiResult permits
        ForceReplyKeyboard,
        InlineKeyboardMarkup,
        ReplyKeyboardRemove,
        ReplyKeyboardMarkup {

}
