package org.teleight.teleightbots.api.objects.origin;

public sealed interface MessageOrigin permits
        MessageOriginChannel,
        MessageOriginChat,
        MessageOriginHiddenUser,
        MessageOriginUser {
}
