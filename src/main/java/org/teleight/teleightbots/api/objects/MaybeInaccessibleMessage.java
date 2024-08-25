package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.serialization.deserializers.MaybeInaccessibleMessageDeserializer;

@JsonDeserialize(contentUsing = MaybeInaccessibleMessageDeserializer.class)
public sealed interface MaybeInaccessibleMessage extends ApiResult permits
        Message,
        InaccessibleMessage {
}
