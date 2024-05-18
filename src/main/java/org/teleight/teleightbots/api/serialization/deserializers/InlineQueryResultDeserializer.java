package org.teleight.teleightbots.api.serialization.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.teleight.teleightbots.api.objects.InlineQueryResult;
import org.teleight.teleightbots.api.objects.InlineQueryResultPhoto;

import java.io.IOException;

public class InlineQueryResultDeserializer extends StdDeserializer<InlineQueryResult> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public InlineQueryResultDeserializer() {
        super((Class<?>) null);
    }

    @Override
    public InlineQueryResult deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        if(!node.has("type")){
            return null;
        }
        switch (node.get("type").asText()) {
            case "photo":
                if (node.has("photo_url")) {
                    return objectMapper.readValue(node.toString(), InlineQueryResultPhoto.class);
                } else {
                    //Todo create cache
                }
        }

        return null;
    }

}
