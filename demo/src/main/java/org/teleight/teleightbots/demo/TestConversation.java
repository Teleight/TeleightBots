package org.teleight.teleightbots.demo;

import org.teleight.teleightbots.api.methods.SendMessage;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.conversation.Conversation;
import org.teleight.teleightbots.conversation.RunningConversation;

public class TestConversation implements Conversation.Executor {
    @Override
    public void execute(Bot bot, RunningConversation conversation) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId("89238932489")
                .text("Inviami un messaggio che dice ciao..")
                .build();
        bot.execute(sendMessage);

        var message = conversation.waitFor(Message.class);
        if (message.text().equals("ciao")) {
            SendMessage sendMessage1 = SendMessage.builder()
                    .chatId("89238932489")
                    .text("Bravo!")
                    .build();
            bot.execute(sendMessage1);
        } else {
            SendMessage sendMessage1 = SendMessage.builder()
                    .chatId("89238932489")
                    .text("Non hai scritto ciao!")
                    .build();
            bot.execute(sendMessage1);
        }
    }
}
