package org.teleight.teleightbots.api.serialization.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.teleight.teleightbots.api.objects.LivePeriod;

import java.io.IOException;

public class LivePeriodSerializer extends JsonSerializer<LivePeriod> {

    @Override
    public void serialize(LivePeriod color, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(color.getSeconds());
    }

}

