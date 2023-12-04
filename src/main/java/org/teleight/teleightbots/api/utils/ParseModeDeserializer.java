package org.teleight.teleightbots.api.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ParseModeDeserializer extends StdDeserializer<ParseMode> {

    public ParseModeDeserializer() {
        super(ParseMode.class);
    }

    @Override
    public ParseMode deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JacksonException {
        return ParseMode.valueOf(jsonParser.getText().toUpperCase());
    }

}
