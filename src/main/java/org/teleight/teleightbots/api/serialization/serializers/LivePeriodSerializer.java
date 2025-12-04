package org.teleight.teleightbots.api.serialization.serializers;

import org.teleight.teleightbots.api.objects.LivePeriod;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public class LivePeriodSerializer extends ValueSerializer<LivePeriod> {

    @Override
    public void serialize(LivePeriod value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        gen.writeNumber(value.getSeconds());
    }

}

