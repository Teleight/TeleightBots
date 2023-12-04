package org.teleight.teleightbots.api.objects.chat.member;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class ChatMemberDeserializer extends StdDeserializer<ChatMember> {

    private static final String FIELD = "status";

    private final ObjectMapper objectMapper;

    public ChatMemberDeserializer() {
        this(null);
    }

    private ChatMemberDeserializer(Class<?> vc) {
        super(vc);
        objectMapper = new ObjectMapper();
    }

    @Override
    public ChatMember deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        if(!node.has(FIELD)){
            return null;
        }
        final JsonNode statusField = node.get(FIELD);
        final String statusFieldAsString = statusField.asText();

        final ChatMemberType wrappedMemberType = findWrappedMemberType(statusFieldAsString);
        if(wrappedMemberType == null){
            return null;
        }
        return objectMapper.readValue(node.toString(), wrappedMemberType.getWrapperClass());
    }

    private @Nullable ChatMemberType findWrappedMemberType(String statusFieldAsString){
        ChatMemberType wrappedMemberType = null;
        for (ChatMemberType chatMemberType : ChatMemberType.values()) {
            if(chatMemberType.getFieldValue().equals(statusFieldAsString)){
                wrappedMemberType = chatMemberType;
            }
        }
        return wrappedMemberType;
    }

}
