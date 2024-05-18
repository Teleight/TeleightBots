package org.teleight.teleightbots.api.serialization.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.objects.BotCommandScope;

import java.io.IOException;

public class BotCommandScopeDeserializer extends StdDeserializer<BotCommandScope> {

    private static final String FIELD = "status";

    private final ObjectMapper objectMapper;

    public BotCommandScopeDeserializer() {
        super((Class<?>) null);
        objectMapper = new ObjectMapper();
    }

    @Override
    public BotCommandScope deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        if(!node.has(FIELD)){
            return null;
        }
        final JsonNode statusField = node.get(FIELD);
        final String statusFieldAsString = statusField.asText();

        final BotCommandScope.BotCommandScopeType wrappedBotCommandScopeType = findWrappedType(statusFieldAsString);
        if(wrappedBotCommandScopeType == null){
            return null;
        }
        return objectMapper.readValue(node.toString(), wrappedBotCommandScopeType.getWrapperClass());
    }

    private @Nullable BotCommandScope.BotCommandScopeType findWrappedType(String statusFieldAsString){
        BotCommandScope.BotCommandScopeType wrappedMemberType = null;
        for (BotCommandScope.BotCommandScopeType botCommandScopeType : BotCommandScope.BotCommandScopeType.values()) {
            if(botCommandScopeType.getFieldValue().equals(statusFieldAsString)){
                wrappedMemberType = botCommandScopeType;
            }
        }
        return wrappedMemberType;
    }

}
