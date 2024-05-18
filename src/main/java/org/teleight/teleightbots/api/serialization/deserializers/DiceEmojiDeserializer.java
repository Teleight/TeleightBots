package org.teleight.teleightbots.api.serialization.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.teleight.teleightbots.api.objects.Dice;

import java.io.IOException;

public class DiceEmojiDeserializer extends StdDeserializer<Dice.DiceEmoji> {

    public DiceEmojiDeserializer() {
        super((Class<?>) null);
    }

    @Override
    public Dice.DiceEmoji deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        return Dice.DiceEmoji.valueOf(jsonParser.getText().toUpperCase());
    }

}
