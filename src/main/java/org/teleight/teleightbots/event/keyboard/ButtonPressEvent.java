package org.teleight.teleightbots.event.keyboard;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.methods.callback.CallbackQuery;
import org.teleight.teleightbots.api.methods.inline.AnswerCallbackQuery;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.trait.Event;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

public class ButtonPressEvent implements Event {

    private final Bot bot;
    private final Update update;
    private final AtomicBoolean completed;

    public ButtonPressEvent(@NotNull Bot bot, @NotNull Update update, @NotNull AtomicBoolean completed) {
        this.bot = bot;
        this.update = update;
        this.completed = completed;
    }

    @Override
    public @NotNull Bot bot() {
        return bot;
    }

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

    public @NotNull CompletableFuture<Boolean> completeCallback() {
        return completeCallback(AnswerCallbackQuery.of(callbackQuery().id()));
    }

    public @NotNull CompletableFuture<Boolean> completeCallback(String text) {
        return completeCallback(AnswerCallbackQuery.of(callbackQuery().id()).withText(text));
    }

    public @NotNull CompletableFuture<Boolean> completeCallback(AnswerCallbackQuery answerCallbackQuery) {
        completed.set(true);
        return bot.execute(answerCallbackQuery);
    }

}
