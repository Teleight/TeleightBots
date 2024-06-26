package org.teleight.teleightbots.api.serialization.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.awt.*;
import java.io.IOException;

public class ColorSerializer extends StdSerializer<Color> {
    public ColorSerializer() {
        super((Class<Color>) null);
    }

    @Override
    public void serialize(Color color, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(color.getRGB());
    }
}

