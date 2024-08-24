package org.teleight.teleightbots.api.serialization.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.teleight.teleightbots.api.objects.InputPaidMedia;
import org.teleight.teleightbots.api.objects.InputPaidMediaPhoto;
import org.teleight.teleightbots.api.objects.InputPaidMediaVideo;

import java.io.IOException;

// telegram, why.
public class InputPaidMediasSerializer extends StdSerializer<InputPaidMedia[]> {
    public InputPaidMediasSerializer() {
        super((Class<InputPaidMedia[]>) null);
    }

    @Override
    public void serialize(InputPaidMedia[] paidMedias, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (InputPaidMedia paidMedia : paidMedias) {
            switch (paidMedia) {
                case InputPaidMediaPhoto paidMediaPhoto -> {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeStringField("type", "photo");
                    if (paidMediaPhoto.media().file() != null) {
                        jsonGenerator.writeStringField("media", "attach://" + paidMediaPhoto.media().fileName());
                    } else {
                        jsonGenerator.writeStringField("media", paidMediaPhoto.media().id());
                    }
                    jsonGenerator.writeEndObject();
                }
                case InputPaidMediaVideo paidMediaVideo -> {
                    jsonGenerator.writeStartObject();
                    jsonGenerator.writeStringField("type", "video");
                    if (paidMediaVideo.media().file() != null) {
                        jsonGenerator.writeStringField("media", "attach://" + paidMediaVideo.media().fileName());
                    } else {
                        jsonGenerator.writeStringField("media", paidMediaVideo.media().id());
                    }
                    if (paidMediaVideo.thumbnail() != null) {
                        if (paidMediaVideo.thumbnail().file() != null) {
                            jsonGenerator.writeStringField("thumbnail", "attach://" + paidMediaVideo.thumbnail().fileName());
                        } else {
                            jsonGenerator.writeStringField("thumbnail", paidMediaVideo.thumbnail().id());
                        }
                    }
                    jsonGenerator.writeNumberField("width", paidMediaVideo.width());
                    jsonGenerator.writeNumberField("height", paidMediaVideo.height());
                    jsonGenerator.writeNumberField("duration", paidMediaVideo.duration());
                    jsonGenerator.writeBooleanField("supportsStreaming", paidMediaVideo.supportsStreaming());
                    jsonGenerator.writeEndObject();
                }
                default -> throw new IllegalStateException("Unexpected value: " + paidMedia);
            }
        }
        jsonGenerator.writeEndArray();
    }
}

