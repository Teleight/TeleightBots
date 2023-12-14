package org.teleight.teleightbots.event.trait;

import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.chat.Chat;

public interface GroupBotEvent extends Event {

    Update update();

    default Chat chat() {
        if (update().myChatMember() != null) {
            return update().myChatMember().chat();
        } else {
            return update().message().chat();
        }
    }

}
