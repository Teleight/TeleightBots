package org.teleight.teleightbots.api.objects.chat.boost.source;

public sealed interface ChatBoostSource permits
        ChatBoostSourceGiftCode,
        ChatBoostSourceGiveaway,
        ChatBoostSourcePremium {
}
