package org.teleight.teleightbots.api;

import java.io.Serializable;
import java.util.Map;

/**
 * Interface representing a multipart API method.
 * <p>
 * This interface extends the {@link ApiMethod} interface and provides methods to access parameters and input files,
 * as well as a method to build a request with a multipart body.
 *
 * @param <R> the type of the response expected from the API method
 */
public interface MultiPartApiMethod<R extends Serializable> extends ApiMethod<R> {

    /**
     * Gets the parameters of the multipart API method.
     *
     * @return a map containing the parameters of the multipart API method
     */
    Map<String, Object> getParameters();

}
