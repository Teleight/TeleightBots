package org.teleight.teleightbots.api.serialization.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.teleight.teleightbots.api.objects.LivePeriod;

import java.io.IOException;

public class LivePeriodDeserializer extends JsonDeserializer<LivePeriod> {

    @Override
    public LivePeriod deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return LivePeriod.fromSeconds(jsonParser.getIntValue());
    }

}
