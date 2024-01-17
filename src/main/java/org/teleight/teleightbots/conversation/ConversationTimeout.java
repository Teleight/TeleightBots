package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.concurrent.TimeUnit;

public record ConversationTimeout(
	@Range(from = 0, to = 10_000L)
	int timeout,

	@NotNull
    TimeUnit timeUnit
) {

}
