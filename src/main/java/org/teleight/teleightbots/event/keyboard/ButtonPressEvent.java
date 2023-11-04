package org.teleight.teleightbots.event.keyboard;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.methods.callback.CallbackQuery;
import org.teleight.teleightbots.api.methods.inline.AnswerCallbackQuery;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.event.trait.Event;

import java.util.concurrent.CompletableFuture;

public record ButtonPressEvent(
        @NotNull Bot bot,
        @NotNull CallbackQuery callbackQuery
) implements Event {

    public @NotNull CompletableFuture<Boolean> completeCallback() {
        return bot.execute(new AnswerCallbackQuery(callbackQuery.id()));
    }

}
