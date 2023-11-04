package org.teleight.teleightbots.demo;

import org.teleight.teleightbots.TeleightBots;
import org.teleight.teleightbots.api.methods.SendMessage;
import org.teleight.teleightbots.api.menu.Menu;
import org.teleight.teleightbots.api.menu.MenuButton;
import org.teleight.teleightbots.bot.BotSettings;
import org.teleight.teleightbots.event.EventListener;
import org.teleight.teleightbots.event.bot.UpdateReceivedEvent;

public class MainDemo {

    public static void main(String[] args) {
        TeleightBots teleightBots = TeleightBots.init();
        teleightBots.start();

        final String botToken = System.getenv("bot.token") != null ? System.getenv("bot.token") : "--INSERT-TOKEN-HERE--";
        final String botUsername = System.getenv("bot.username") != null ? System.getenv("bot.username") : "--INSERT-USERNAME--HERE";
        final String chatId = "--INSERT-YOUR-ID-HERE--";

        EventListener<UpdateReceivedEvent> updateEvent = EventListener.builder(UpdateReceivedEvent.class)
                .handler(event -> System.out.println("UpdateReceivedEvent: " + event.bot().getBotUsername() + " -> " + event))
                .build();

        TeleightBots.getBotManager().registerLongPolling(botToken, botUsername, BotSettings.DEFAULT, bot -> {
            System.out.println("Bot registered: " + bot.getBotUsername());

            bot.getEventManager().addListener(updateEvent);

            Menu menu = bot.createMenu((context, rootMenu) -> {
                Menu subMenu2 = context.createMenu("subMenu2");
                Menu subMenu3 = context.createMenu("subMenu3");

                rootMenu.setText("Root menu");
                subMenu2.setText("SubMenu 2");
                subMenu3.setText("SubMenu 3");


                MenuButton button1_1 = MenuButton.builder().text("menu 1 - bottone 1").destinationMenu(subMenu2).build();

                MenuButton button2_1 = MenuButton.builder().text("menu 2 - bottone 1").build();
                MenuButton button2_2 = MenuButton.builder().text("menu 2 - bottone 2").destinationMenu(rootMenu).build();
                MenuButton button2_3 = MenuButton.builder().text("menu 2 - bottone 3").destinationMenu(subMenu3).build();

                MenuButton button3_1 = MenuButton.builder().text("menu 3 - bottone 4").destinationMenu(subMenu2).build();


                rootMenu.addRow(button1_1);

                subMenu2.addRow(button2_1, button2_2)
                        .addRow(button2_3);

                subMenu3.addRow(button3_1);
            });


            SendMessage sendMessage = SendMessage.builder()
                    .text("Test message")
                    .chatId(chatId)
                    .replyMarkup(menu.getKeyboard())
                    .build();

            bot.execute(sendMessage);
        });
    }

}
