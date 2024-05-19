package org.teleight.teleightbots.api.serialization.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.teleight.teleightbots.api.ApiMethod;

import java.io.IOException;

public class CommonEnumValueDeserializer<T extends Enum<T> & ApiMethod.SimpleFieldValueProvider> extends StdDeserializer<T> {

    private final Class<T> enumClass;

    public CommonEnumValueDeserializer(Class<T> enumClass) {
        super(enumClass);
        this.enumClass = enumClass;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return T.valueOf(enumClass, jsonParser.getText().toUpperCase());
    }
}
