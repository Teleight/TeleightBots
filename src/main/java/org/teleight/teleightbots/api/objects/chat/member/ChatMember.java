package org.teleight.teleightbots.api.objects.chat.member;

public sealed interface ChatMember permits
        ChatMemberOwner,
        ChatMemberAdministrator,
        ChatMemberMember,
        ChatMemberRestricted,
        ChatMemberLeft,
        ChatMemberBanned {
}
