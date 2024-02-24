package org.teleight.teleightbots.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.ArrayType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.ApiResponse;
import org.teleight.teleightbots.api.objects.MaybeInaccessibleMessage;
import org.teleight.teleightbots.api.objects.chat.boost.source.ChatBoostSource;
import org.teleight.teleightbots.api.objects.chat.boost.source.serialization.ChatBoostSourceDeserializer;
import org.teleight.teleightbots.api.objects.chat.member.ChatMember;
import org.teleight.teleightbots.api.objects.chat.member.ChatMemberDeserializer;
import org.teleight.teleightbots.api.objects.inline.result.InlineQueryResult;
import org.teleight.teleightbots.api.objects.inline.result.InlineQueryResultDeserializer;
import org.teleight.teleightbots.api.objects.keyboard.ReplyKeyboard;
import org.teleight.teleightbots.api.objects.keyboard.serialization.KeyboardDeserializer;
import org.teleight.teleightbots.api.objects.origin.MessageOrigin;
import org.teleight.teleightbots.api.objects.origin.serialization.MessageOriginDeserializer;
import org.teleight.teleightbots.api.objects.serialization.MaybeInaccessibleMessageDeserializer;
import org.teleight.teleightbots.api.utils.*;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

import java.awt.*;
import java.io.IOException;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ApiMethod<R> {

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
                    .addDeserializer(ChatMember.class, new ChatMemberDeserializer())
            )
            .registerModule(new SimpleModule()
                    .addDeserializer(InlineQueryResult.class,new InlineQueryResultDeserializer())
            )
            .registerModule(new SimpleModule()
                    .addDeserializer(MessageOrigin.class, new MessageOriginDeserializer())
            )
            .registerModule(new SimpleModule()
                    .addDeserializer(MaybeInaccessibleMessage.class, new MaybeInaccessibleMessageDeserializer())
            )
            .registerModule(new SimpleModule()
                    .addDeserializer(ChatBoostSource.class, new ChatBoostSourceDeserializer())
            );

    @NotNull R deserializeResponse(@NotNull String answer) throws TelegramRequestException;

    @NotNull String getEndpointURL();

    default @NotNull R deserializeResponse(@NotNull String answer, @NotNull Class<R> returnClass) throws TelegramRequestException {
        final JavaType type = OBJECT_MAPPER.getTypeFactory().constructType(returnClass);
        return UNSAFE_deserializeResponse(answer, type);
    }

    default <K> @NotNull R deserializeResponseArray(@NotNull String answer, @NotNull Class<K> returnClass) throws TelegramRequestException {
        final ArrayType collectionType = OBJECT_MAPPER.getTypeFactory().constructArrayType(returnClass);
        return UNSAFE_deserializeResponse(answer, collectionType);
    }

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
