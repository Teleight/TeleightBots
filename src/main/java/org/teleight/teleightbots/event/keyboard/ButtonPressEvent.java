package org.teleight.teleightbots.event.keyboard;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.methods.AnswerCallbackQuery;
import org.teleight.teleightbots.api.objects.CallbackQuery;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.MaybeInaccessibleMessage;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.TelegramBot;
import org.teleight.teleightbots.event.trait.Event;

import java.util.concurrent.CompletableFuture;

public record ButtonPressEvent(
        @NotNull TelegramBot bot,
        @NotNull Update update
) implements Event {

    public @Nullable CallbackQuery callbackQuery() {
        return update.callbackQuery();
    }

    public @Nullable User from() {
        final CallbackQuery callbackQuery = callbackQuery();
        if (callbackQuery == null) return null;
        return callbackQuery.from();
    }

    public @Nullable MaybeInaccessibleMessage message() {
        final CallbackQuery callbackQuery = callbackQuery();
        if (callbackQuery == null) return null;
        return callbackQuery.message();
    }

    public @Nullable Chat chat() {
        final MaybeInaccessibleMessage maybeInaccessibleMessage = message();
        if (!(maybeInaccessibleMessage instanceof Message message)) {
            return null;
        }
        return message.chat();
    }

    public @Nullable String chatId() {
        final Chat chat = chat();
        if (chat == null) return null;
        return chat.id();
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
