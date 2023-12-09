package org.teleight.teleightbots.conversation;

import org.teleight.teleightbots.bot.Bot;

public interface Conversation {

    String getName();

//    default <T extends ApiResult> T waitFor(@NotNull Class<T> resultType) {
//        return waitFor(resultType, 0, TimeUnit.MILLISECONDS);
//    }
//
//    <T extends ApiResult> T waitFor(@NotNull Class<T> resultType, long timeout, @NotNull TimeUnit unit);

    @FunctionalInterface
    interface Executor {
        void execute(Bot bot, RunningConversation conversation);
    }
}
