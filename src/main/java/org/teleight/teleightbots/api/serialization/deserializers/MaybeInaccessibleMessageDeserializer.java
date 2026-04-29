package org.teleight.teleightbots.api.serialization.deserializers;

import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.InaccessibleMessage;
import org.teleight.teleightbots.api.objects.MaybeInaccessibleMessage;
import org.teleight.teleightbots.api.objects.Message;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;

public class MaybeInaccessibleMessageDeserializer extends ValueDeserializer<MaybeInaccessibleMessage> {

    @Override
    public MaybeInaccessibleMessage deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        final JsonNode node = p.readValueAsTree();
        if (!node.has("date")) {
            return null;
        }
        final Class<? extends MaybeInaccessibleMessage> wrapperClass;
        final int date = node.get("date").asInt(0);
        if (date == 0) {
            wrapperClass = InaccessibleMessage.class;
        } else {
            wrapperClass = Message.class;
        }
        return ApiMethod.OBJECT_MAPPER.readValue(node.toString(), wrapperClass);
    }

}
