package org.teleight.teleightbots.api;

import org.teleight.teleightbots.utils.MultiPartBodyPublisher;

public interface MultiPartApiMethod<R> extends ApiMethod<R> {

    void buildRequest(MultiPartBodyPublisher bodyCreator);

}
