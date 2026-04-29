package org.teleight.teleightbots.api.serialization.deserializers;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

import java.awt.*;

public class ColorDeserializer extends ValueDeserializer<Color> {

    @Override
    public Color deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        return new Color(p.getIntValue());
    }

}
