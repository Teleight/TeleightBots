package org.teleight.teleightbots.api.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ParseModeSerializer extends StdSerializer<ParseMode> {

    public ParseModeSerializer() {
        super((Class<ParseMode>) null);
    }

    @Override
    public void serialize(ParseMode value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.getFieldValue());
    }

}
