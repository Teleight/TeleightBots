package org.teleight.teleightbots.api.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.awt.*;
import java.io.IOException;

public class ColorDeserializer extends StdDeserializer<Color> {
    public ColorDeserializer() {
        super((Class<Color>) null);
    }

    @Override
    public Color deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return new Color(jsonParser.getIntValue());
    }
}
