package org.teleight.teleightbots.event.trait;

import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.ChatMemberUpdated;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.Update;

/**
 * Interface for a group bot event.
 */
public interface GroupBotEvent extends Event {

    /**
     * Gets the Update object associated with the event.
     *
     * @return the Update object associated with the event
     */
    Update update();

    /**
     * Gets the Chat object associated with the event.
     *
     * @return the Chat object associated with the event
     */
    default Chat chat() {
        if (update().myChatMember() != null) {
            return update().myChatMember().chat();
        } else {
            return update().message().chat();
        }
    }

}
