package org.teleight.teleightbots.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.teleight.teleightbots.utils.MultiPartBodyPublisher;

public interface MultiPartApiMethod<R> extends ApiMethod<R> {

    void buildRequest(MultiPartBodyPublisher bodyCreator) throws JsonProcessingException;

}
