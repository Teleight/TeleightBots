package org.teleight.teleightbots.demo.conversations;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.methods.send.SendMessage;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.conversation.Conversation;
import org.teleight.teleightbots.conversation.ConversationContext;
import org.teleight.teleightbots.conversation.ConversationTimeout;

import java.util.concurrent.TimeUnit;

public class TestConversation implements Conversation {

    @Override
    public void execute(@NotNull ConversationContext context) {
        final Chat chat = context.chat();
        final long chatId = chat.id();

        final SendMessage.SendMessageBuilder sendMessageBuilder = SendMessage.builder()
                .chatId(chatId);

        // Let's start the conversation by sending a message to the user
        context.bot().execute(sendMessageBuilder.text("Send me a messages with the text \"hello\"").build());

        // Now, let's wait for an update. We give the user 10 seconds to reply.
        // If the result is null, that means the bot did not receive an update in time
        final Update update = context.waitForUpdate(10, TimeUnit.SECONDS);

        if (update == null) {
            // And this is the case, so let's leave the conversation and send an error message to the user
            context.bot().execute(sendMessageBuilder.text("you didnt send the message in time..").build());
            return;
        }

        // Let's get a message from the update
        final Message message = update.message();

        if(message == null){
            // The message is null, and it's not what we want
            // This happens when the bot receives an update which does not contain a message.
            context.bot().execute(sendMessageBuilder.text("You didnt send a message..").build());
            return;
        }

        // Let's console print the message sent by the user, just for fun
        System.out.println("Message: " + message.text());

        // Now, check if the text equals hello or not and act appropriately
        if (message.text() == null || !message.text().equals("hello")) {
            context.bot().execute(sendMessageBuilder.text("You didn't send \"hello\"!").build());
        } else {
            context.bot().execute(sendMessageBuilder.text("Good job!").build());
        }
    }

    @Override
    public @NotNull String name() {
        return "test";
    }

    @Override
    public @NotNull ConversationTimeout conversationTimeout() {
        return new ConversationTimeout(10, TimeUnit.SECONDS);
    }

}
