package org.teleight.teleightbots.api.objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.ApiResult;

public sealed interface ChatMember extends ApiResult permits
        ChatMemberOwner,
        ChatMemberAdministrator,
        ChatMemberMember,
        ChatMemberRestricted,
        ChatMemberLeft,
        ChatMemberBanned {

    String TYPE_NAME = "type";

    @JsonProperty(TYPE_NAME)
    ChatMemberType type();

    User user();

    default boolean isAdmin() {
        return this instanceof ChatMemberOwner || this instanceof ChatMemberAdministrator;
    }

    enum ChatMemberType implements ApiMethod.WrappedFieldValueProvider<ChatMember> {

        OWNER("creator", ChatMemberOwner.class),
        ADMINISTRATOR("administrator", ChatMemberAdministrator.class),
        MEMBER("member", ChatMemberMember.class),
        RESTRICTED("restricted", ChatMemberRestricted.class),
        LEFT("left", ChatMemberLeft.class),
        BANNED("kicked", ChatMemberBanned.class);

        private final String fieldValue;
        private final Class<? extends ChatMember> wrapperClass;

        ChatMemberType(String fieldValue, Class<? extends ChatMember> wrapperClass) {
            this.fieldValue = fieldValue;
            this.wrapperClass = wrapperClass;
        }

        @Override
        public String getFieldValue() {
            return fieldValue;
        }

        @Override
        public Class<? extends ChatMember> getWrapperClass() {
            return wrapperClass;
        }

        @Override
        public String getFieldName() {
            return TYPE_NAME;
        }
    }

}
