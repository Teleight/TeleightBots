package org.teleight.teleightbots.api.objects;

public sealed interface MaybeInaccessibleMessage permits
        Message,
        InaccessibleMessage {
}
