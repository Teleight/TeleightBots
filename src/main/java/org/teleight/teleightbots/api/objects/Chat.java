package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiResult;

public record Chat(
        @JsonProperty(value = "id", required = true)
        String id,

        @JsonProperty(value = "type", required = true)
        String type,

        @JsonProperty("title")
        @Nullable
        String title,

        @JsonProperty("username")
        @Nullable
        String username,

        @JsonProperty("first_name")
        @Nullable
        String firstName,

        @JsonProperty("last_name")
        @Nullable
        String lastName,

        @JsonProperty("is_forum")
        boolean isForum
) implements ApiResult {

    @JsonIgnore
    public boolean isGroup() {
        return Type.GROUP_CHAT_TYPE.valueField.equals(type);
    }

    @JsonIgnore
    public boolean isChannel() {
        return Type.CHANNEL_CHAT_TYPE.valueField.equals(type);
    }

    @JsonIgnore
    public boolean isUser() {
        return Type.USER_CHAT_TYPE.valueField.equals(type);
    }

    @JsonIgnore
    public boolean isSuperGroup() {
        return Type.SUPERGROUP_CHAT_TYPE.valueField.equals(type);
    }

    public enum Type {
        USER_CHAT_TYPE("private"),
        GROUP_CHAT_TYPE("group"),
        CHANNEL_CHAT_TYPE("channel"),
        SUPERGROUP_CHAT_TYPE("supergroup");

        private final String valueField;

        Type(String valueField) {
            this.valueField = valueField;
        }
    }

}
