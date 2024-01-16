package org.teleight.teleightbots.demo.conversations;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.teleight.teleightbots.api.methods.SendMessage;
import org.teleight.teleightbots.conversation.Conversation;
import org.teleight.teleightbots.conversation.ConversationContext;

import java.util.concurrent.TimeUnit;

public class TestConversation implements Conversation {

    @Override
    public void execute(@NotNull ConversationContext context) {
        var chat = context.chat();
        var chatId = chat.id();

        SendMessage startMessage = SendMessage.builder()
                .chatId(chat.id())
                .text("Send me a messages with the text \"hello\"")
                .build();
        context.execute(startMessage);

        var update = context.waitForUpdate(10, TimeUnit.SECONDS);
        if (update == null) {
            SendMessage resultToUser = SendMessage.builder()
                    .chatId(chatId)
                    .text("you didnt send the message in time..")
                    .build();
            context.execute(resultToUser);
            return;
        }
        var message = update.message();
        if(message == null){
            SendMessage resultToUser = SendMessage.builder()
                    .chatId(chatId)
                    .text("You didnt send a message..")
                    .build();
            context.execute(resultToUser);
            return;
        }
        System.out.println("Message: " + message.text());


        if (message.text().equals("hello")) {
            SendMessage resultToUser = SendMessage.builder()
                    .chatId(chatId)
                    .text("Good job!")
                    .build();
            context.execute(resultToUser);
        } else {
            SendMessage resultToUser = SendMessage.builder()
                    .chatId(chatId)
                    .text("You didn't send \"hello\"!")
                    .build();
            context.execute(resultToUser);
        }
    }

    @Override
    public @NotNull String name() {
        return "test";
    }

    @Override
    public @Range(from = 0, to = 10_000L) int conversationTimeout() {
        return 10;
    }

    @Override
    public @NotNull TimeUnit conversationTimeoutUnit() {
        return TimeUnit.SECONDS;
    }

}
