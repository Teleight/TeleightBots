package org.teleight.teleightbots.api.serialization.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.teleight.teleightbots.api.objects.ChatAction;

import java.io.IOException;

public class ChatActionSerializer extends StdSerializer<ChatAction> {

    public ChatActionSerializer() {
        super((Class<ChatAction>) null);
    }

    @Override
    public void serialize(ChatAction chatAction, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(chatAction.getFieldValue());
    }
}
