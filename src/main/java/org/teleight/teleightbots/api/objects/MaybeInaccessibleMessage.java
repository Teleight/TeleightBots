package org.teleight.teleightbots.api.objects;

import org.teleight.teleightbots.api.ApiResult;

public sealed interface MaybeInaccessibleMessage extends ApiResult permits
        Message,
        InaccessibleMessage {
}
