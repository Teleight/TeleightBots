package org.teleight.teleightbots.event.keyboard;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.methods.AnswerCallbackQuery;
import org.teleight.teleightbots.api.objects.CallbackQuery;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.trait.Event;

import java.util.concurrent.CompletableFuture;

public record ButtonPressEvent(
        @NotNull Bot bot,
        @NotNull Update update
) implements Event {

    public @NotNull CallbackQuery callbackQuery() {
        return update.callbackQuery();
    }

    public @NotNull User from() {
        return callbackQuery().from();
    }

    public @Nullable Message message() {
        return callbackQuery().message();
    }

    public @NotNull Chat chat() {
        return message().chat();
    }

    public @NotNull String chatId() {
        return chat().id().toString();
    }

    public @NotNull CompletableFuture<Boolean> answer() {
        if (update.callbackQuery() == null) return CompletableFuture.completedFuture(false);
        return bot.execute(AnswerCallbackQuery.ofBuilder(update.callbackQuery().id()).build());
    }

    public @NotNull CompletableFuture<Boolean> answer(String text) {
        if (update.callbackQuery() == null) return CompletableFuture.completedFuture(false);
        return bot.execute(AnswerCallbackQuery.ofBuilder(update.callbackQuery().id()).text(text).build());
    }

}
