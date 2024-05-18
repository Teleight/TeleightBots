package org.teleight.teleightbots.api.objects;

import org.teleight.teleightbots.api.ApiResult;

public sealed interface MessageOrigin extends ApiResult permits
        MessageOriginChannel,
        MessageOriginChat,
        MessageOriginHiddenUser,
        MessageOriginUser {
}
