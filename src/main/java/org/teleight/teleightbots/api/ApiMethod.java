package org.teleight.teleightbots.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.ArrayType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.ApiResponse;
import org.teleight.teleightbots.api.objects.ChatAction;
import org.teleight.teleightbots.api.objects.ChatBoostSource;
import org.teleight.teleightbots.api.objects.ChatMember;
import org.teleight.teleightbots.api.objects.Dice;
import org.teleight.teleightbots.api.objects.InlineQueryResult;
import org.teleight.teleightbots.api.objects.InputSticker;
import org.teleight.teleightbots.api.objects.MaybeInaccessibleMessage;
import org.teleight.teleightbots.api.objects.MessageOrigin;
import org.teleight.teleightbots.api.objects.ParseMode;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.serialization.deserializers.ChatActionDeserializer;
import org.teleight.teleightbots.api.serialization.deserializers.ChatBoostSourceDeserializer;
import org.teleight.teleightbots.api.serialization.deserializers.ChatMemberDeserializer;
import org.teleight.teleightbots.api.serialization.deserializers.ColorDeserializer;
import org.teleight.teleightbots.api.serialization.deserializers.DateDeserializer;
import org.teleight.teleightbots.api.serialization.deserializers.DiceEmojiDeserializer;
import org.teleight.teleightbots.api.serialization.deserializers.InlineQueryResultDeserializer;
import org.teleight.teleightbots.api.serialization.deserializers.InputStickerFormatDeserializer;
import org.teleight.teleightbots.api.serialization.deserializers.KeyboardDeserializer;
import org.teleight.teleightbots.api.serialization.deserializers.MaybeInaccessibleMessageDeserializer;
import org.teleight.teleightbots.api.serialization.deserializers.MessageOriginDeserializer;
import org.teleight.teleightbots.api.serialization.deserializers.ParseModeDeserializer;
import org.teleight.teleightbots.api.serialization.serializers.ChatActionSerializer;
import org.teleight.teleightbots.api.serialization.serializers.ColorSerializer;
import org.teleight.teleightbots.api.serialization.serializers.DateSerializer;
import org.teleight.teleightbots.api.serialization.serializers.DiceEmojiSerializer;
import org.teleight.teleightbots.api.serialization.serializers.InputStickerFormatSerializer;
import org.teleight.teleightbots.api.serialization.serializers.ParseModeSerializer;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * Base interface for all Telegram Bot API methods.
 *
 * @param <R> result type of the method
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ApiMethod<R extends Serializable> {

    /**
     * The object mapper used to serialize and deserialize objects to and from JSON.
     */
    ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new SimpleModule()
                    .addDeserializer(ReplyKeyboard.class, new KeyboardDeserializer())
            )
            .registerModule(new SimpleModule()
                    .addSerializer(ParseMode.class, new ParseModeSerializer())
                    .addDeserializer(ParseMode.class, new ParseModeDeserializer())
            )
            .registerModule(new SimpleModule()
                    .addSerializer(Color.class, new ColorSerializer())
                    .addDeserializer(Color.class, new ColorDeserializer())
            )
            .registerModule(new SimpleModule()
                    .addSerializer(Date.class, new DateSerializer())
                    .addDeserializer(Date.class, new DateDeserializer())
            )
            .registerModule(new SimpleModule()
                    .addSerializer(InputSticker.Format.class, new InputStickerFormatSerializer())
                    .addDeserializer(InputSticker.Format.class, new InputStickerFormatDeserializer())
            )
            .registerModule(new SimpleModule()
                    .addDeserializer(ChatMember.class, new ChatMemberDeserializer())
            )
            .registerModule(new SimpleModule()
                    .addDeserializer(InlineQueryResult.class, new InlineQueryResultDeserializer())
            )
            .registerModule(new SimpleModule()
                    .addDeserializer(MessageOrigin.class, new MessageOriginDeserializer())
            )
            .registerModule(new SimpleModule()
                    .addDeserializer(MaybeInaccessibleMessage.class, new MaybeInaccessibleMessageDeserializer())
            )
            .registerModule(new SimpleModule()
                    .addSerializer(ChatAction.class, new ChatActionSerializer())
                    .addDeserializer(ChatAction.class, new ChatActionDeserializer())
            )
            .registerModule(new SimpleModule()
                    .addSerializer(Dice.DiceEmoji.class, new DiceEmojiSerializer())
                    .addDeserializer(Dice.DiceEmoji.class, new DiceEmojiDeserializer())
            )
            .registerModule(new SimpleModule()
                    .addDeserializer(ChatBoostSource.class, new ChatBoostSourceDeserializer())
            );

    /**
     * Returns the endpoint URL for the Telegram Bot API method.
     *
     * @return the endpoint URL
     */
    @NotNull String getEndpointURL();

    /**
     * Deserializes the response from the Telegram Bot API.
     *
     * @param answer the response from the Telegram Bot API
     * @return the deserialized response
     * @throws TelegramRequestException if an error occurs while deserializing the response
     */
    default @NotNull R deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, new TypeReference<R>() {});
    }

    /**
     * Deserializes the response from the Telegram Bot API into a specific type reference.
     *
     * @param answer        the response from the Telegram Bot API
     * @param typeReference the type reference to deserialize the response into
     * @return the deserialized response
     * @throws TelegramRequestException if an error occurs while deserializing the response
     */
    default @NotNull R deserializeResponse(@NotNull String answer, @NotNull TypeReference<R> typeReference) throws TelegramRequestException {
        final JavaType type = OBJECT_MAPPER.getTypeFactory().constructType(typeReference.getType());
        return UNSAFE_deserializeResponse(answer, type);
    }

    /**
     * Deserializes the response from the Telegram Bot API into a specific class.
     *
     * @param answer      the response from the Telegram Bot API
     * @param returnClass the class to deserialize the response into
     * @return the deserialized response
     * @throws TelegramRequestException if an error occurs while deserializing the response
     */
    default @NotNull R deserializeResponse(@NotNull String answer, @NotNull Class<R> returnClass) throws TelegramRequestException {
        final JavaType type = OBJECT_MAPPER.getTypeFactory().constructType(returnClass);
        return UNSAFE_deserializeResponse(answer, type);
    }

    /**
     * Deserializes the response from the Telegram Bot API into an array of a specific class.
     *
     * @param answer      the response from the Telegram Bot API
     * @param returnClass the class to deserialize the response into
     * @param <K>         the class to deserialize the response into
     * @return the deserialized response
     * @throws TelegramRequestException if an error occurs while deserializing the response
     */
    default <K extends Serializable> @NotNull R deserializeResponseArray(@NotNull String answer, @NotNull Class<K> returnClass) throws TelegramRequestException {
        final ArrayType collectionType = OBJECT_MAPPER.getTypeFactory().constructArrayType(returnClass);
        return UNSAFE_deserializeResponse(answer, collectionType);
    }

    /**
     * Deserializes the response from the Telegram Bot API into a specific serializable class
     *
     * @param answer      the response from the Telegram Bot API
     * @param returnClass the class to deserialize the response into
     * @param <K>         the class to deserialize the response into
     * @return the deserialized response
     * @throws TelegramRequestException if an error occurs while deserializing the response
     */
    default <K extends Serializable> R deserializeResponseSerializable(String answer, Class<K> returnClass) throws TelegramRequestException {
        JavaType type = OBJECT_MAPPER.getTypeFactory().constructType(returnClass);
        return UNSAFE_deserializeResponse(answer, type);
    }

    /**
     * Deserializes the response from the Telegram Bot API into a specific JavaType.
     * <p>
     * This method is marked as internal and should not be used directly.
     * Use {@link #deserializeResponse(String, Class)} or {@link #deserializeResponseArray(String, Class)} instead.
     * </p>
     *
     * @param answer the response from the Telegram Bot API
     * @param type   the JavaType to deserialize the response into
     * @return the deserialized response
     * @throws TelegramRequestException if an error occurs while deserializing the response
     */
    @ApiStatus.Internal
    private @NotNull R UNSAFE_deserializeResponse(@NotNull String answer, @NotNull JavaType type) throws TelegramRequestException {
        try {
            final JavaType responseType = OBJECT_MAPPER.getTypeFactory().constructParametricType(ApiResponse.class, type);
            final ApiResponse<R> result = OBJECT_MAPPER.readValue(answer, responseType);
            if (result.ok()) {
                return result.result();
            } else {
                throw new TelegramRequestException(String.format("Error executing %s query", this.getClass().getName()), result);
            }
        } catch (IOException e) {
            throw new TelegramRequestException("Unable to deserialize response", e);
        }
    }

}
