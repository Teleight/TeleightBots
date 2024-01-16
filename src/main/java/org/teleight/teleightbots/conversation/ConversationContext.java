package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.bot.Bot;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@ApiStatus.Internal
public final class ConversationContext {

    private final Bot bot;
    private final Chat chat;
    private final RunningConversation runningConversation;

    public ConversationContext(@NotNull Bot bot, @NotNull Chat chat, RunningConversation runningConversation) {
        this.bot = bot;
        this.chat = chat;
        this.runningConversation = runningConversation;
    }

    public Bot bot() {
        return bot;
    }

    public Chat chat() {
        return chat;
    }

    public @Nullable Update waitForUpdate() {
        return waitForUpdate(0, TimeUnit.MILLISECONDS);
    }

    public @Nullable Update waitForUpdate(long timeout, @NotNull TimeUnit unit) {
        return runningConversation.waitForUpdate(timeout, unit);
    }

    public <R> @NotNull CompletableFuture<R> execute(@NotNull ApiMethod<R> method) {
        return bot.execute(method);
    }

}
