package org.teleight.teleightbots.api.serialization.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.teleight.teleightbots.api.objects.ChatAction;

import java.io.IOException;

public class ChatActionDeserializer extends StdDeserializer<ChatAction> {

    public ChatActionDeserializer() {
        super((Class<?>) null);
    }

    @Override
    public ChatAction deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        return ChatAction.valueOf(jsonParser.getText().toUpperCase());
    }

}
