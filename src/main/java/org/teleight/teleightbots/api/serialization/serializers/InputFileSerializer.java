package org.teleight.teleightbots.api.serialization.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.teleight.teleightbots.api.objects.InputFile;

import java.io.IOException;

public class InputFileSerializer extends JsonSerializer<InputFile> {

    @Override
    public void serialize(InputFile inputFile, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (inputFile.file() != null) {
            jsonGenerator.writeObject("attach://" + inputFile.fileName());
        } else {
            jsonGenerator.writeObject(inputFile.id());
        }
    }

}
