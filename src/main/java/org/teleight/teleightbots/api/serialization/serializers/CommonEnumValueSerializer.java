package org.teleight.teleightbots.api.serialization.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.teleight.teleightbots.api.ApiMethod;

import java.io.IOException;

public class CommonEnumValueSerializer<T extends Enum<T> & ApiMethod.SimpleFieldValueProvider> extends StdSerializer<T> {

    public CommonEnumValueSerializer(Class<T> enumClass) {
        super(enumClass);
    }

    @Override
    public void serialize(T value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.getFieldValue());
    }

}
