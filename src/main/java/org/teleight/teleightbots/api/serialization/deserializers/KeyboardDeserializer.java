package org.teleight.teleightbots.api.serialization.deserializers;

import org.teleight.teleightbots.api.objects.ForceReplyKeyboard;
import org.teleight.teleightbots.api.objects.InlineKeyboardMarkup;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.ReplyKeyboardMarkup;
import org.teleight.teleightbots.api.objects.ReplyKeyboardRemove;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;

import static org.teleight.teleightbots.api.ApiMethod.OBJECT_MAPPER;

public class KeyboardDeserializer extends ValueDeserializer<ReplyKeyboard> {

    @Override
    public ReplyKeyboard deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        final JsonNode node = p.readValueAsTree();
        Class<? extends ReplyKeyboard> wrapperClass = null;
        if (node.has("inline_keyboard")) {
            wrapperClass = InlineKeyboardMarkup.class;
        }
        if (node.has("keyboard")) {
            wrapperClass = ReplyKeyboardMarkup.class;
        }
        if (node.has("remove_keyboard")) {
            wrapperClass = ReplyKeyboardRemove.class;
        }
        if (node.has("force_reply")) {
            wrapperClass = ForceReplyKeyboard.class;
        }
        return OBJECT_MAPPER.readValue(node.toString(), wrapperClass);
    }

}
