package org.teleight.teleightbots.api.serialization.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.InaccessibleMessage;
import org.teleight.teleightbots.api.objects.MaybeInaccessibleMessage;
import org.teleight.teleightbots.api.objects.Message;

import java.io.IOException;

public class MaybeInaccessibleMessageDeserializer extends StdDeserializer<MaybeInaccessibleMessage> {

    public MaybeInaccessibleMessageDeserializer() {
        super((Class<MaybeInaccessibleMessage>) null);
    }

    @Override
    public MaybeInaccessibleMessage deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
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
