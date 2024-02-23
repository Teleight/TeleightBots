package org.teleight.teleightbots.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.teleight.teleightbots.utils.MultiPartBodyPublisher;

/**
 * Interface representing a multipart API method.
 * <p>
 * This interface extends the ApiMethod interface and provides a method to build a request with a multipart body.
 *
 * @param <R> the type of the response expected from the API method
 */
public interface MultiPartApiMethod<R> extends ApiMethod<R> {

    /**
     * Builds a request with a multipart body.
     *
     * @param bodyCreator the MultiPartBodyPublisher used to create the multipart body
     * @throws JsonProcessingException if there is an error processing the JSON
     */
    void buildRequest(MultiPartBodyPublisher bodyCreator) throws JsonProcessingException;

}
