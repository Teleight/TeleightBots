package org.teleight.teleightbots.api;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Message;

/**
 * Interface representing a multipart API method that returns a Message.
 */
public interface MultiPartApiMethodMessage extends MultiPartApiMethod<Message> {

    /**
     * Deserializes the response from the API method into a Message object.
     *
     * @param answer the response from the API method as a String
     * @return the deserialized response as a Message object
     */
    default @NotNull Message deserializeResponse(@NotNull String answer) {
        return deserializeResponse(answer, Message.class);
    }

}
