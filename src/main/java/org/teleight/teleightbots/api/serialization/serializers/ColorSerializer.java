package org.teleight.teleightbots.api.serialization.serializers;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.awt.*;

public class ColorSerializer extends ValueSerializer<Color> {

    @Override
    public void serialize(Color value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        gen.writeNumber(value.getRGB());
    }

}

