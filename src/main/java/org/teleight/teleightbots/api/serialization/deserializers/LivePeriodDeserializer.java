package org.teleight.teleightbots.api.serialization.deserializers;

import org.teleight.teleightbots.api.objects.LivePeriod;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

public class LivePeriodDeserializer extends ValueDeserializer<LivePeriod> {

    @Override
    public LivePeriod deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        return LivePeriod.fromSeconds(p.getIntValue());
    }

}
