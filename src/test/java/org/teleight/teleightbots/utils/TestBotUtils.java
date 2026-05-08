package org.teleight.teleightbots.utils;

import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.LongPollingTelegramBot;
import org.teleight.teleightbots.bot.settings.LongPollingBotSettings;

public class TestBotUtils {

    private TestBotUtils() {
    }

    public static LongPollingTelegramBot newBot(String username) {
        return LongPollingTelegramBot.create("token", username, LongPollingBotSettings.DEFAULT);
    }

    public static User newUser(long id) {
        return new User(
                id,
                false,
                "First",
                "Last",
                null,
                null,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
        );
    }

    public static Chat newChat(String id) {
        return new Chat(
                id,
                "private",
                null,
                null,
                null,
                null,
                false,
                false
        );
    }


}
