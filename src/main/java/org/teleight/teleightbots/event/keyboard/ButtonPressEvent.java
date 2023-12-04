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
import org.teleight.teleightbots.event.trait.IngoingEvent;

import java.util.concurrent.CompletableFuture;

public record ButtonPressEvent(
        @NotNull Bot bot,
        @NotNull Update update
) implements IngoingEvent {

    public @NotNull CallbackQuery callbackQuery(){
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

    public @NotNull String chatId(){
        return chat().id().toString();
    }

    public @NotNull CompletableFuture<Boolean> completeCallback() {
        return bot.execute(new AnswerCallbackQuery(callbackQuery().id()));
    }

}
