package org.teleight.teleightbots.api.serialization.serializers;

import org.teleight.teleightbots.api.objects.InputFile;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public class InputFileSerializer extends ValueSerializer<InputFile> {

    @Override
    public void serialize(InputFile value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        if (value.file() != null) {
            gen.writePOJO("attach://" + value.fileName());
        } else {
            gen.writePOJO(value.id());
        }
    }

}
