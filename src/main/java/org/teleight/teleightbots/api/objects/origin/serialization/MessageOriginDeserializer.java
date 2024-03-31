package org.teleight.teleightbots.api.objects.origin.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.origin.*;

import java.io.IOException;

public class MessageOriginDeserializer extends StdDeserializer<MessageOrigin> {

    public MessageOriginDeserializer() {
        this(null);
    }

    private MessageOriginDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public MessageOrigin deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (!node.has("type")) {
            return null;
        }
        Class<? extends MessageOrigin> wrapperClass = null;
        final String type = node.get("type").asText();
        if (type.equals("user")) {
            wrapperClass = MessageOriginUser.class;
        }
        if (type.equals("hidden_user")) {
            wrapperClass = MessageOriginHiddenUser.class;
        }
        if(type.equals("chat")){
            wrapperClass = MessageOriginChat.class;
        }
        if (type.equals("channel")) {
            wrapperClass = MessageOriginChannel.class;
        }
        if (wrapperClass == null) {
            return null;
        }
        return ApiMethod.OBJECT_MAPPER.readValue(node.toString(), wrapperClass);
    }

}
