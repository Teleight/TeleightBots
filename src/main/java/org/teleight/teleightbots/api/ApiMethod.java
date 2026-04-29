package org.teleight.teleightbots.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.ApiResponse;
import org.teleight.teleightbots.api.objects.MaybeInaccessibleMessage;
import org.teleight.teleightbots.api.objects.ReplyKeyboard;
import org.teleight.teleightbots.api.serialization.deserializers.ColorDeserializer;
import org.teleight.teleightbots.api.serialization.deserializers.KeyboardDeserializer;
import org.teleight.teleightbots.api.serialization.deserializers.MaybeInaccessibleMessageDeserializer;
import org.teleight.teleightbots.api.serialization.serializers.ColorSerializer;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.module.SimpleModule;
import tools.jackson.databind.type.ArrayType;

import java.awt.*;
import java.io.Serializable;

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
    JsonMapper OBJECT_MAPPER = JsonMapper.builder()
            .disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
            .addModule(new SimpleModule()
                    .addDeserializer(ReplyKeyboard.class, new KeyboardDeserializer())
            )
            .addModule(new SimpleModule()
                    .addSerializer(Color.class, new ColorSerializer())
                    .addDeserializer(Color.class, new ColorDeserializer())
            )
            .addModule(new SimpleModule()
                    .addDeserializer(MaybeInaccessibleMessage.class, new MaybeInaccessibleMessageDeserializer())
            )
            .build();

    /**
     * Returns the endpoint URL for the Telegram Bot API method.
     *
     * @return the endpoint URL
     */
    @NotNull
    String getEndpointURL();

    /**
     * Deserializes the response from the Telegram Bot API.
     *
     * @param answer the response from the Telegram Bot API
     * @return the deserialized response
     * @throws TelegramRequestException if an error occurs while deserializing the response
     */
    @ApiStatus.Internal
    @NotNull
    R deserializeResponse(@NotNull String answer) throws TelegramRequestException;

    /**
     * Deserializes the response from the Telegram Bot API into a specific class.
     *
     * @param answer      the response from the Telegram Bot API
     * @param returnClass the class to deserialize the response into
     * @return the deserialized response
     * @throws TelegramRequestException if an error occurs while deserializing the response
     */
    @ApiStatus.Internal
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
    @ApiStatus.Internal
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
    @ApiStatus.Internal
    default <K extends Serializable> R deserializeResponseSerializable(String answer, Class<K> returnClass) throws TelegramRequestException {
        final JavaType type = OBJECT_MAPPER.getTypeFactory().constructType(returnClass);
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
        } catch (JacksonException e) {
            throw new TelegramRequestException("Unable to deserialize response", e);
        }
    }
}
