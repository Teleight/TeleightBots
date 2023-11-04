package org.teleight.teleightbots.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ArrayType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.ApiResponse;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ApiMethod<R> {

    ObjectMapper objectMapper = new ObjectMapper();

    @NotNull R deserializeResponse(@NotNull String answer) throws TelegramRequestException;

    @NotNull String getEndpointURL();

    default @NotNull R deserializeResponse(@NotNull String answer, @NotNull Class<R> returnClass) throws TelegramRequestException {
        final JavaType type = objectMapper.getTypeFactory().constructType(returnClass);
        return UNSAFE_deserializeResponse(answer, type);
    }

    default <K> @NotNull R deserializeResponseArray(@NotNull String answer, @NotNull Class<K> returnClass) throws TelegramRequestException {
        final ArrayType collectionType = objectMapper.getTypeFactory().constructArrayType(returnClass);
        return UNSAFE_deserializeResponse(answer, collectionType);
    }

    @ApiStatus.Internal
    private @NotNull R UNSAFE_deserializeResponse(@NotNull String answer, @NotNull JavaType type) throws TelegramRequestException {
        try {
            final JavaType responseType = objectMapper.getTypeFactory().constructParametricType(ApiResponse.class, type);
            final ApiResponse<R> result = objectMapper.readValue(answer, responseType);
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
