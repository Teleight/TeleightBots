package org.teleight.teleightbots.api.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.teleight.teleightbots.api.objects.input.InputSticker;

import java.io.IOException;

public class InputStickerFormatDeserializer extends StdDeserializer<InputSticker.Format> {
    public InputStickerFormatDeserializer() {
        super((Class<InputSticker.Format>) null);
    }

    @Override
    public InputSticker.Format deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return InputSticker.Format.valueOf(jsonParser.getText().toUpperCase());
    }
}
