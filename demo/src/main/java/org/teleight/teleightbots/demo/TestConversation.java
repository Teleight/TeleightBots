package org.teleight.teleightbots.demo;

import org.teleight.teleightbots.api.methods.send.SendMessage;
import org.teleight.teleightbots.api.objects.chat.Chat;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.conversation.Conversation;
import org.teleight.teleightbots.conversation.RunningConversation;

import java.util.concurrent.TimeUnit;

public class TestConversation implements Conversation.Executor {
    @Override
    public void execute(Bot bot, Chat chat, RunningConversation conversation) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chat.id())
                .text("Send me a messages with the text \"hello\"")
                .build();
        bot.execute(sendMessage);

        var update = conversation.waitForUpdate(2, TimeUnit.SECONDS);
        if (update == null) {
            SendMessage sendMessage1 = SendMessage.builder()
                    .chatId(chat.id())
                    .text("you didnt send the message in time..")
                    .build();
            bot.execute(sendMessage1);
            return;
        }
        var message = update.message();
        System.out.println("Message: " + message.text());


        if (message.text().equals("hello")) {
            SendMessage sendMessage1 = SendMessage.builder()
                    .chatId(chat.id())
                    .text("Good job!")
                    .build();
            bot.execute(sendMessage1);
        } else {
            SendMessage sendMessage1 = SendMessage.builder()
                    .chatId(chat.id())
                    .text("You didn't send \"hello\"!")
                    .build();
            bot.execute(sendMessage1);
        }
    }
}
