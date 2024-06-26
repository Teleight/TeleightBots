package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.serialization.WrappedFieldValueProvider;

public sealed interface MessageOrigin extends ApiResult permits
        MessageOriginChannel,
        MessageOriginChat,
        MessageOriginHiddenUser,
        MessageOriginUser {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    MessageOrigin.MessageOriginType type();

    enum MessageOriginType implements WrappedFieldValueProvider<MessageOrigin> {

        CHANNEL("channel", MessageOriginChannel.class),
        CHAT("chat", MessageOriginChat.class),
        HIDDEN_USER("hidden_user", MessageOriginHiddenUser.class),
        USER("user", MessageOriginUser.class);

        private final String fieldValue;
        private final Class<? extends MessageOrigin> wrapperClass;

        MessageOriginType(String fieldValue, Class<? extends MessageOrigin> wrapperClass) {
            this.fieldValue = fieldValue;
            this.wrapperClass = wrapperClass;
        }

        @Override
        public String getFieldValue() {
            return fieldValue;
        }

        @Override
        public Class<? extends MessageOrigin> getWrapperClass() {
            return wrapperClass;
        }

        @Override
        public String getFieldName() {
            return TYPE_NAME;
        }
    }

}
