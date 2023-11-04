package org.teleight.teleightbots.api.objects.keyboard;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class KeyboardDeserializer extends StdDeserializer<ReplyKeyboard> {

    private final ObjectMapper objectMapper;

    public KeyboardDeserializer() {
        this(null);
    }

    private KeyboardDeserializer(Class<?> vc) {
        super(vc);
        objectMapper = new ObjectMapper();
    }

    @Override
    public ReplyKeyboard deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        if (node.has("inline_keyboard")) {
            return objectMapper.readValue(node.toString(), InlineKeyboardMarkup.class);
        }

        return null;
    }

}
