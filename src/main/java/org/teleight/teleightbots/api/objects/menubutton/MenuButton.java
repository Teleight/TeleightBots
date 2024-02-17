package org.teleight.teleightbots.api.objects.menubutton;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;

public interface MenuButton extends ApiResult {

    @JsonProperty(value = "type")
    String type();

}
