package org.teleight.teleightbots.api.objects;

import org.teleight.teleightbots.api.ApiResult;

public sealed interface InputMessageContent extends ApiResult permits
        InputContactMessageContent,
        InputInvoiceMessageContent,
        InputLocationMessageContent,
        InputTextMessageContent,
        InputVenueMessageContent {
}
