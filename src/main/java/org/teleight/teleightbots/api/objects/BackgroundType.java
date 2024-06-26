package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiResult;
import org.teleight.teleightbots.api.serialization.WrappedFieldValueProvider;

public sealed interface BackgroundType extends ApiResult permits
        BackgroundTypeFill,
        BackgroundTypeWallpaper,
        BackgroundTypePattern,
        BackgroundTypeChatTheme {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    BackgroundType.BackgroundTypeType type();

    enum BackgroundTypeType implements WrappedFieldValueProvider<BackgroundType> {

        FILL("fill", BackgroundTypeFill.class),
        WALLPAPER("wallpaper", BackgroundTypeWallpaper.class),
        PATTERN("pattern", BackgroundTypePattern.class),
        CHAT_THEME("chat_theme", BackgroundTypeChatTheme.class);

        private final String fieldValue;
        private final Class<? extends BackgroundType> wrapperClass;

        BackgroundTypeType(String fieldValue, Class<? extends BackgroundType> wrapperClass) {
            this.fieldValue = fieldValue;
            this.wrapperClass = wrapperClass;
        }

        @Override
        public Class<? extends BackgroundType> getWrapperClass() {
            return wrapperClass;
        }

        @Override
        public String getFieldName() {
            return TYPE_NAME;
        }

        @Override
        public String getFieldValue() {
            return fieldValue;
        }
    }

}
