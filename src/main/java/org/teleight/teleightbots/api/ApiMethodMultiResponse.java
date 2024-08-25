package org.teleight.teleightbots.api;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.exception.exceptions.TelegramRequestException;

import java.io.Serializable;
import java.util.List;

/**
 * Base API method that supports requests with multiple response types
 */
public interface ApiMethodMultiResponse extends ApiMethod<Serializable> {

    /**
     * Retrieves the list of classes representing possible types for deserialization of the response.
     *
     * @return A {@code List} of classes extending {@code Serializable}.
     */
    @NotNull List<Class<? extends Serializable>> getSerializableClasses();

    /**
     * Deserializes the response from a string answer.
     *
     * @param answer The string representing the response.
     * @return The deserialized response object.
     * @throws TelegramRequestException If an error occurs during deserialization.
     */
    @Override
    default @NotNull Serializable deserializeResponse(@NotNull String answer) throws TelegramRequestException {
        return deserializeResponse(answer, getSerializableClasses());
    }

    /**
     * Deserializes the response from a string answer using a list of possible classes.
     *
     * @param answer         The string representing the response.
     * @param possibleValues The list of possible classes for deserialization.
     * @return The deserialized response object. Null if an error occurs during deserialization.
     * @throws TelegramRequestException If an error occurs during deserialization.
     */
    default Serializable deserializeResponse(String answer, List<Class<? extends Serializable>> possibleValues) throws TelegramRequestException {
        for (Class<? extends Serializable> possibleValue : possibleValues) {
            try {
                return deserializeResponseSerializable(answer, possibleValue);
            } catch (TelegramRequestException e) {
                throw new TelegramRequestException("Unable to deserialize response", e);
            }
        }
        return null;
    }

}
