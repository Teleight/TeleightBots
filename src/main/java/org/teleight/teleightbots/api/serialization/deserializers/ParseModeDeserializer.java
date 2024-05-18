package org.teleight.teleightbots.api.serialization.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.teleight.teleightbots.api.objects.ParseMode;

import java.io.IOException;

public class ParseModeDeserializer extends StdDeserializer<ParseMode> {

    public ParseModeDeserializer() {
        super((Class<?>) null);
    }

    @Override
    public ParseMode deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JacksonException {
        return ParseMode.valueOf(jsonParser.getText().toUpperCase());
    }

}
