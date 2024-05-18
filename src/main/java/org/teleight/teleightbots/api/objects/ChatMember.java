package org.teleight.teleightbots.api.objects;

import org.teleight.teleightbots.api.ApiResult;

public sealed interface ChatMember extends ApiResult permits
        ChatMemberOwner,
        ChatMemberAdministrator,
        ChatMemberMember,
        ChatMemberRestricted,
        ChatMemberLeft,
        ChatMemberBanned {

    ChatMemberType type();

    User user();

    default boolean isAdmin(){
        return this instanceof ChatMemberOwner || this instanceof ChatMemberAdministrator;
    }

    enum ChatMemberType {

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

        public String getFieldValue() {
            return fieldValue;
        }

        public Class<? extends ChatMember> getWrapperClass() {
            return wrapperClass;
        }

    }


}
