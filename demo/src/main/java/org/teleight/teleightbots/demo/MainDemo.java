package org.teleight.teleightbots.demo;

import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.api.ApiMethod;
import org.teleight.teleightbots.api.methods.DeleteWebhook;
import org.teleight.teleightbots.api.methods.SendMessage;
import org.teleight.teleightbots.api.objects.InlineKeyboardButton;
import org.teleight.teleightbots.api.objects.ParseMode;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.bot.settings.LongPollingBotSettings;
import org.teleight.teleightbots.bot.settings.WebhookBotSettings;
import org.teleight.teleightbots.bot.webhook.WebhookMessageHandler;
import org.teleight.teleightbots.conversation.Conversation;
import org.teleight.teleightbots.conversation.ConversationInstanceConstraints;
import org.teleight.teleightbots.conversation.Property;
import org.teleight.teleightbots.demo.command.TestCommand;
import org.teleight.teleightbots.demo.command.TestConversationCommand;
import org.teleight.teleightbots.demo.conversations.TestConversation;
import org.teleight.teleightbots.event.EventListener;
import org.teleight.teleightbots.event.bot.UpdateReceivedEvent;
import org.teleight.teleightbots.menu.Menu;

public class MainDemo {

    public static void main(String[] args) {
        final String botToken = System.getenv("bot_token") != null ? System.getenv("bot_token") : "--INSERT-TOKEN-HERE--";
        final String botUsername = System.getenv("bot_username") != null ? System.getenv("bot_username") : "--INSERT-USERNAME--HERE";
        final String chatId = System.getenv("bot_default_chatid") != null ? System.getenv("bot_default_chatid") : "--INSERT-CHATID--HERE";

        final EventListener<UpdateReceivedEvent> updateEvent = EventListener.ofBuilder(UpdateReceivedEvent.class)
                .handler(event -> System.out.println("UpdateReceivedEvent: " + event.bot().getBotUsername() + " -> " + event))
                .build();

        WebhookBotSettings webhookSettings = WebhookBotSettings.of("https://example.com/webhook");
        TeleightBots.getBotManager().registerWebhook(botToken, botUsername, webhookSettings, update -> {
            System.out.println("WebhookMessageHandler: " + update);
            if (update.message() != null) {
                return SendMessage.ofBuilder(update.message().chatId(), "<b>Test message</b>")
                        .parseMode(ParseMode.HTML)
                        .build();
            }
            return null;
        });

        TeleightBots.getBotManager().registerLongPolling(botToken, botUsername, LongPollingBotSettings.DEFAULT, bot -> {
            System.out.println("Bot registered: " + bot.getBotUsername());

            //Listener
            bot.getEventManager().addListener(updateEvent);

            //Commands
            bot.getCommandManager().registerCommand(new TestCommand());

            Menu menu = bot.createMenu((context, rootMenu) -> {
                Menu subMenu2 = context.createMenu("subMenu2");
                Menu subMenu3 = context.createMenu("subMenu3");

                rootMenu.setText("Root menu");
                subMenu2.setText("SubMenu 2");
                subMenu3.setText("SubMenu 3");


                InlineKeyboardButton button1_1 = InlineKeyboardButton.ofBuilder("menu 1 - button 1").destinationMenu(subMenu2).build();

                InlineKeyboardButton button2_1 = InlineKeyboardButton.ofBuilder("menu 2 - button 1").build();
                InlineKeyboardButton button2_2 = InlineKeyboardButton.ofBuilder("menu 2 - button 2").destinationMenu(rootMenu).build();
                InlineKeyboardButton button2_3 = InlineKeyboardButton.ofBuilder("menu 2 - button 3").destinationMenu(subMenu3).build();

                InlineKeyboardButton button3_1 = InlineKeyboardButton.ofBuilder("menu 3 - button 4").destinationMenu(subMenu2).build();


                rootMenu.addRow(button1_1);

                subMenu2.addRow(button2_1, button2_2)
                        .addRow(button2_3);

                subMenu3.addRow(button3_1);
            });

            SendMessage sendMessage = SendMessage.ofBuilder(chatId, "<b>Test message</b>")
                    .replyMarkup(menu.getKeyboard())
                    .parseMode(ParseMode.HTML)
                    .build();
            bot.execute(sendMessage);

            Conversation testConversation = Conversation.ofBuilder("test", new TestConversation())
                    .property(Property.of("test"))
                    .allowUnknownProperties(true)
                    .instanceConstraints(ConversationInstanceConstraints.ofBuilder().maxInstances(1).build())
                    .build();
            bot.getConversationManager().registerConversation(testConversation);

            bot.getCommandManager().registerCommand(new TestConversationCommand());
        });
    }

}
