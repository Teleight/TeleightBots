package org.teleight.teleightbots.api.serialization.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.serialization.WrappedFieldValueProvider;

import java.io.IOException;

import static org.teleight.teleightbots.api.ApiMethod.OBJECT_MAPPER;

public class WrappedResultTypeDeserializer<T extends ApiResult, E extends Enum<E> & WrappedFieldValueProvider<T>> extends StdDeserializer<T> {

    private final Class<E> enumType;

    public WrappedResultTypeDeserializer(Class<T> wrapperClass, Class<E> enumType) {
        super(wrapperClass);
        this.enumType = enumType;
    }

    @Override
    public T deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        WrappedFieldValueProvider<T> wrappedMemberType = findWrappedMemberType(node, enumType);
        if (wrappedMemberType == null) {
            return null;
        }
        return OBJECT_MAPPER.readValue(node.toString(), wrappedMemberType.getWrapperClass());
    }

    private WrappedFieldValueProvider<T> findWrappedMemberType(JsonNode node, Class<E> enumType) {
        for (E enumConstant : enumType.getEnumConstants()) {
            if (node.has(enumConstant.getFieldName())) {
                final JsonNode statusField = node.get(enumConstant.getFieldName());
                if (statusField.asText().equals(statusField.asText())) {
                    return enumConstant;
                }
            }
        }
        return null;
    }

}
