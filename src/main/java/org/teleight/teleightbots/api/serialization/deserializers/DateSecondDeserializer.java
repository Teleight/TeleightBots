package org.teleight.teleightbots.api.serialization.deserializers;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

import java.util.Date;

public class DateSecondDeserializer extends ValueDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        return new Date(p.getLongValue() * 1000);
    }

}
