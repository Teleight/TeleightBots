package org.teleight.teleightbots.api.serialization.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.teleight.teleightbots.api.objects.Dice;

import java.io.IOException;

public class DiceEmojiSerializer extends StdSerializer<Dice.DiceEmoji> {

    public DiceEmojiSerializer() {
        super((Class<Dice.DiceEmoji>) null);
    }

    @Override
    public void serialize(Dice.DiceEmoji diceEmoji, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(diceEmoji.getFieldValue());
    }
}
