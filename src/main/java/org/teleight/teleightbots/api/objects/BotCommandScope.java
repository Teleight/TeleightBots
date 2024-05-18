package org.teleight.teleightbots.api.objects;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.ApiResult;

public sealed interface BotCommandScope extends ApiResult permits
        BotCommandScopeDefault,
        BotCommandScopeAllPrivateChats,
        BotCommandScopeAllGroupChats,
        BotCommandScopeAllChatAdministrators,
        BotCommandScopeChat,
        BotCommandScopeChatAdministrators,
        BotCommandScopeChatMember {

    @NotNull
    BotCommandScope.BotCommandScopeType type();

    enum BotCommandScopeType {
        DEFAULT("default", BotCommandScopeDefault.class),
        ALL_PRIVATE_CHATS("all_private_chats", BotCommandScopeAllPrivateChats.class),
        ALL_GROUP_CHATS("all_group_chats", BotCommandScopeAllGroupChats.class),
        ALL_CHAT_ADMINS("all_chat_administrators", BotCommandScopeAllChatAdministrators.class),
        CHAT("chat", BotCommandScopeChat.class),
        CHAT_ADMINS("chat_administrators", BotCommandScopeChatAdministrators.class),
        CHAT_MEMBER("chat_member", BotCommandScopeChatMember.class),;

        private final String fieldValue;
        private final Class<? extends BotCommandScope> wrapperClass;

        BotCommandScopeType(String fieldValue, Class<? extends BotCommandScope> wrapperClass) {
            this.fieldValue = fieldValue;
            this.wrapperClass = wrapperClass;
        }

        public String getFieldValue() {
            return fieldValue;
        }

        public Class<? extends BotCommandScope> getWrapperClass() {
            return wrapperClass;
        }
    }

}
