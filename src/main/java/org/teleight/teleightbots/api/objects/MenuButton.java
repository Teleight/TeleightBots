package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public sealed interface MenuButton extends Serializable permits
        MenuButtonDefault,
        MenuButtonCommands,
        MenuButtonWebApp {

    @JsonProperty(value = "type")
    MenuButtonType type();

    enum MenuButtonType {

        DEFAULT("default", MenuButtonDefault.class),
        COMMANDS("commands", MenuButtonCommands.class),
        WEB_APP("web_app", MenuButtonWebApp.class);

        private final String fieldValue;
        private final Class<? extends MenuButton> wrapperClass;

        MenuButtonType(String fieldValue, Class<? extends MenuButton> wrapperClass) {
            this.fieldValue = fieldValue;
            this.wrapperClass = wrapperClass;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public Class<? extends MenuButton> getWrapperClass() {
            return wrapperClass;
        }

    }

}
