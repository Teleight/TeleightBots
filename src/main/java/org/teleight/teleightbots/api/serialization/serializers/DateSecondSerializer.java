package org.teleight.teleightbots.api.serialization.serializers;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.util.Date;

public class DateSecondSerializer extends ValueSerializer<Date> {

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        // Telegram API uses seconds, not milliseconds
        gen.writeNumber(value.getTime() / 1000);
    }

}
