package org.teleight.teleightbots.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;
import org.teleight.teleightbots.utils.MultiPartBodyPublisher;

/**
 * Interface representing a multipart API method that returns a Boolean.
 */
public interface MultiPartApiMethodBoolean extends ApiMethod<Boolean> {

    /**
     * Builds a request with a multipart body.
     *
     * @param bodyCreator the MultiPartBodyPublisher used to create the multipart body
     * @throws JsonProcessingException if there is an error processing the JSON
     */
    void buildRequest(MultiPartBodyPublisher bodyCreator) throws JsonProcessingException;

    /**
     * Deserializes the response from the API method into a Boolean object.
     *
     * @param answer the response from the API method as a String
     * @return the deserialized response as a Boolean
     * @throws TelegramRequestException if there is an error processing the request
     */
    @Override
    default @NotNull Boolean deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, Boolean.class);
    }
}
