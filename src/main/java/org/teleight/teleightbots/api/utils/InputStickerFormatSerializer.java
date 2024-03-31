package org.teleight.teleightbots.api.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.teleight.teleightbots.api.objects.input.InputSticker;

import java.io.IOException;

public class InputStickerFormatSerializer extends StdSerializer<InputSticker.Format> {
    public InputStickerFormatSerializer() {
        super((Class<InputSticker.Format>) null);
    }

    @Override
    public void serialize(InputSticker.Format color, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(color.getValue());
    }
}

