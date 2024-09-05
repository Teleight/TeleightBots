package org.teleight.teleightbots.api.objects;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

import java.util.Date;

public sealed interface MaybeInaccessibleMessage extends ApiResult permits
        Message,
        InaccessibleMessage {

    int messageId();

    @NotNull Chat chat();

    @NotNull Date date();

}
