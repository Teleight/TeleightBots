package org.teleight.teleightbots.api.objects.chat.member;

import org.teleight.teleightbots.api.objects.User;

public sealed interface ChatMember permits
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

}
