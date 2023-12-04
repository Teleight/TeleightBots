package org.teleight.teleightbots.event.trait;

import org.jetbrains.annotations.NotNull;
import org.teleight.teleightbots.api.objects.Update;

public interface IngoingEvent extends Event {

    @NotNull Update update();

}
