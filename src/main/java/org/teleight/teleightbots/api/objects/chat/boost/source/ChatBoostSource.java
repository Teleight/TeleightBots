package org.teleight.teleightbots.api.objects.chat.boost.source;

import org.teleight.teleightbots.api.ApiResult;

public sealed interface ChatBoostSource extends ApiResult permits
        ChatBoostSourceGiftCode,
        ChatBoostSourceGiveaway,
        ChatBoostSourcePremium {
}
