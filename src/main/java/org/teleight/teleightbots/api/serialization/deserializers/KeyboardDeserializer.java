package org.teleight.teleightbots.api.serialization.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.teleight.teleightbots.api.objects.ForceReplyKeyboard;
import org.teleight.teleightbots.api.objects.InlineKeyboardMarkup;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyKeyboardMarkup;
import org.teleight.teleightbots.api.objects.ReplyKeyboardRemove;

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
        Class<? extends ReplyKeyboard> wrapperClass = null;
        if (node.has("inline_keyboard")) {
            wrapperClass = InlineKeyboardMarkup.class;
        }
        if (node.has("keyboard")) {
            wrapperClass = ReplyKeyboardMarkup.class;
        }
        if(node.has("remove_keyboard")){
            wrapperClass = ReplyKeyboardRemove.class;
        }
        if (node.has("force_reply")) {
            wrapperClass = ForceReplyKeyboard.class;
        }
        return objectMapper.readValue(node.toString(), wrapperClass);
    }

}
