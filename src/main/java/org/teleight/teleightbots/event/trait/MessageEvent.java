package org.teleight.teleightbots.event.trait;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.methods.send.SendMessage;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.ReplyParameters;
import org.teleight.teleightbots.api.objects.Update;

import java.util.concurrent.CompletableFuture;

public interface MessageEvent extends Event {

    Update update();

    default @NotNull CompletableFuture<Message> reply(String text) {
        final Update update = update();
        final Message message = update.message();
        if (message == null) {
            return CompletableFuture.completedFuture(null);
        }
        return bot().execute(SendMessage.builder()
                .text(text)
                .replyParameters(ReplyParameters.builder().messageId(message.messageId()).build())
                .chatId(message.chat().id())
                .build()
        );
    }

}
