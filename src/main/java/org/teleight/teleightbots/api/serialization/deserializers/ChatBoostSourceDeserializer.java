package org.teleight.teleightbots.api.serialization.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.teleight.teleightbots.api.objects.ChatBoostSource;
import org.teleight.teleightbots.api.objects.ChatBoostSourceGiftCode;
import org.teleight.teleightbots.api.objects.ChatBoostSourceGiveaway;
import org.teleight.teleightbots.api.objects.ChatBoostSourcePremium;

import java.io.IOException;

public class ChatBoostSourceDeserializer extends StdDeserializer<ChatBoostSource> {

    private final ObjectMapper objectMapper;

    public ChatBoostSourceDeserializer() {
        this(null);
    }

    private ChatBoostSourceDeserializer(Class<?> vc) {
        super(vc);
        objectMapper = new ObjectMapper();
    }

    @Override
    public ChatBoostSource deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if (!node.has("source")) {
            return null;
        }
        Class<? extends ChatBoostSource> wrapperClass = null;
        final String source = node.get("source").asText();
        if(source .equals("premium")){
            wrapperClass = ChatBoostSourcePremium.class;
        }
        if (source .equals("gift_code")) {
            wrapperClass = ChatBoostSourceGiftCode.class;
        }
        if(source .equals("giveaway")){
            wrapperClass = ChatBoostSourceGiveaway.class;
        }
        if(wrapperClass == null){
            return null;
        }
        return objectMapper.readValue(node.toString(), wrapperClass);
    }

}
