package org.teleight.teleightbots.api.serialization.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.teleight.teleightbots.api.objects.LivePeriod;

import java.io.IOException;

public class LivePeriodSerializer extends StdSerializer<LivePeriod> {
    public LivePeriodSerializer() {
        super((Class<LivePeriod>) null);
    }

    @Override
    public void serialize(LivePeriod color, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(color.getSeconds());
    }
}

