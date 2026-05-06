package org.teleight.teleightbots.conversation;

import org.junit.jupiter.api.Test;
import org.teleight.teleightbots.api.objects.Chat;
import org.teleight.teleightbots.api.objects.Update;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.LongPollingTelegramBot;
import org.teleight.teleightbots.conversation.constraint.ConversationInstanceConstraint;
import org.teleight.teleightbots.conversation.constraint.MaxInstancesConstraint;
import org.teleight.teleightbots.conversation.constraint.MaxInstancesPerChatConstraint;
import org.teleight.teleightbots.event.bot.UpdateReceivedEvent;
import org.teleight.teleightbots.utils.TestBotUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class ConversationManagerTest {

    @Test
    void registerConversation_isRetrievableByName() {
        ConversationManager manager = newManager();
        manager.registerConversation(blocking("flow"));

        assertNotNull(manager.getConversation("flow"));
        assertEquals(1, manager.getConversations().size());
    }

    @Test
    void registerConversation_throwsOnDuplicateName() {
        ConversationManager manager = newManager();
        Conversation conv = blocking("flow");
        manager.registerConversation(conv);

        assertThrows(IllegalArgumentException.class, () -> manager.registerConversation(conv));
    }

    @Test
    void getConversation_returnsNullForUnknownName() {
        ConversationManager manager = newManager();
        assertNull(manager.getConversation("does-not-exist"));
    }

    @Test
    void removeConversation_reducesCount() {
        ConversationManager manager = newManager();
        manager.registerConversation(blocking("flow"));
        manager.removeConversation("flow");

        assertEquals(0, manager.getConversations().size());
        assertNull(manager.getConversation("flow"));
    }

    @Test
    void removeConversation_throwsWhenNotRegistered() {
        ConversationManager manager = newManager();
        assertThrows(IllegalArgumentException.class, () -> manager.removeConversation("missing"));
    }

    @Test
    void joinConversation_returnsNotFoundWhenUnregistered() {
        ConversationManager manager = newManager();
        User user = TestBotUtils.newUser(1L);
        Chat chat = TestBotUtils.newChat("1");

        assertInstanceOf(ConversationManager.JoinResult.ConversationNotFound.class,
                manager.joinConversation(user, chat, "missing"));
    }

    @Test
    void joinConversation_succeedsAndTracksUser() {
        ConversationManager manager = newManager();
        CountDownLatch latch = new CountDownLatch(1);
        manager.registerConversation(conversation("flow", List.of(), latch));

        User user = TestBotUtils.newUser(1L);
        Chat chat = TestBotUtils.newChat("1");

        try {
            assertInstanceOf(ConversationManager.JoinResult.Success.class,
                    manager.joinConversation(user, chat, "flow"));
            assertTrue(manager.isUserInConversation(user));
        } finally {
            manager.leaveConversation(user);
            latch.countDown();
        }
    }

    @Test
    void joinConversation_returnsAlreadyInConversationForActiveUser() {
        ConversationManager manager = newManager();
        CountDownLatch latch = new CountDownLatch(1);
        manager.registerConversation(conversation("flow", List.of(), latch));

        User user = TestBotUtils.newUser(1L);
        Chat chat = TestBotUtils.newChat("1");

        try {
            manager.joinConversation(user, chat, "flow");
            assertInstanceOf(ConversationManager.JoinResult.AlreadyInConversation.class,
                    manager.joinConversation(user, chat, "flow"));
        } finally {
            manager.leaveConversation(user);
            latch.countDown();
        }
    }

    @Test
    void joinConversation_enforcesMaxInstancesConstraint() {
        ConversationManager manager = newManager();
        CountDownLatch latch = new CountDownLatch(1);
        manager.registerConversation(conversation("flow", List.of(new MaxInstancesConstraint(1)), latch));

        User first = TestBotUtils.newUser(1L);
        User second = TestBotUtils.newUser(2L);
        Chat chat = TestBotUtils.newChat("1");

        try {
            assertInstanceOf(ConversationManager.JoinResult.Success.class,
                    manager.joinConversation(first, chat, "flow"));

            ConversationManager.JoinResult.InstanceConstraintReached reached =
                    assertInstanceOf(ConversationManager.JoinResult.InstanceConstraintReached.class,
                            manager.joinConversation(second, chat, "flow"));
            assertInstanceOf(MaxInstancesConstraint.class, reached.constraint());
        } finally {
            manager.leaveConversation(first);
            latch.countDown();
        }
    }

    @Test
    void joinConversation_enforcesMaxInstancesPerChatConstraint() {
        ConversationManager manager = newManager();
        CountDownLatch latch = new CountDownLatch(1);
        manager.registerConversation(conversation("flow", List.of(new MaxInstancesPerChatConstraint(1)), latch));

        User first = TestBotUtils.newUser(1L);
        User second = TestBotUtils.newUser(2L);
        Chat chat = TestBotUtils.newChat("1");

        try {
            assertInstanceOf(ConversationManager.JoinResult.Success.class,
                    manager.joinConversation(first, chat, "flow"));

            ConversationManager.JoinResult.InstanceConstraintReached reached =
                    assertInstanceOf(ConversationManager.JoinResult.InstanceConstraintReached.class,
                            manager.joinConversation(second, chat, "flow"));
            assertInstanceOf(MaxInstancesPerChatConstraint.class, reached.constraint());
        } finally {
            manager.leaveConversation(first);
            latch.countDown();
        }
    }

    @Test
    void leaveConversation_clearsUserActiveState() {
        ConversationManager manager = newManager();
        CountDownLatch latch = new CountDownLatch(1);
        manager.registerConversation(conversation("flow", List.of(), latch));

        User user = TestBotUtils.newUser(1L);
        manager.joinConversation(user, TestBotUtils.newChat("1"), "flow");
        assertTrue(manager.isUserInConversation(user));

        manager.leaveConversation(user);
        latch.countDown();

        assertFalse(manager.isUserInConversation(user));
    }

    @Test
    void leaveConversation_throwsWhenUserNotInConversation() {
        ConversationManager manager = newManager();
        assertThrows(IllegalStateException.class, () -> manager.leaveConversation(TestBotUtils.newUser(1L)));
    }

    @Test
    void joinConversation_throwsWhenRequiredPropertyMissing() {
        ConversationManager manager = newManager();
        Conversation conversation = Conversation.ofBuilder("flow", _ -> {})
                .property(Property.of("required"))
                .build();
        manager.registerConversation(conversation);

        assertThrows(IllegalArgumentException.class,
                () -> manager.joinConversation(TestBotUtils.newUser(1L), TestBotUtils.newChat("1"), "flow"));
    }

    @Test
    void joinConversation_throwsOnUnknownPropertyWhenNotAllowed() {
        ConversationManager manager = newManager();
        Conversation conversation = Conversation.ofBuilder("flow", _ -> {})
                .allowUnknownProperties(false)
                .build();
        manager.registerConversation(conversation);

        Map<String, Object> properties = Map.of("unknown", "value");
        assertThrows(IllegalArgumentException.class,
                () -> manager.joinConversation(TestBotUtils.newUser(1L), TestBotUtils.newChat("1"), "flow", properties));
    }

    @Test
    void joinConversation_appliesKnownAndUnknownPropertiesWhenAllowed() {
        ConversationManager manager = newManager();
        CountDownLatch latch = new CountDownLatch(1);
        Conversation conversation = Conversation.ofBuilder("flow", _ -> await(latch))
                .property(Property.of("required"))
                .allowUnknownProperties(true)
                .build();
        manager.registerConversation(conversation);

        User user = TestBotUtils.newUser(1L);
        Map<String, Object> properties = new HashMap<>();
        properties.put("required", 7);
        properties.put("extra", "ok");

        try {
            assertInstanceOf(ConversationManager.JoinResult.Success.class,
                    manager.joinConversation(user, TestBotUtils.newChat("1"), "flow", properties));

            ConversationContext ctx = manager.getRunningConversations().iterator().next();
            assertEquals(7, ctx.getProperty("required").asInt());
            assertEquals("ok", ctx.getProperty("extra").asString());
            assertNull(ctx.getProperty("missing"));
        } finally {
            manager.leaveConversation(user);
            latch.countDown();
        }
    }

    @Test
    void waitForUpdate_returnsNullOnTimeout() throws InterruptedException {
        LongPollingTelegramBot bot = TestBotUtils.newBot("MyBot");
        ConversationManager manager = ConversationManager.newConversationManager(bot);
        CountDownLatch done = new CountDownLatch(1);
        AtomicReference<Update> received = new AtomicReference<>();

        manager.registerConversation(Conversation.ofBuilder("flow", ctx -> {
            received.set(ctx.waitForUpdate(40, TimeUnit.MILLISECONDS));
            done.countDown();
        }).build());

        manager.joinConversation(TestBotUtils.newUser(1L), TestBotUtils.newChat("1"), "flow");
        assertTrue(done.await(2, TimeUnit.SECONDS));
        assertNull(received.get());
    }

    @Test
    void waitForUpdate_returnsUpdateWhenEventArrives() throws Exception {
        LongPollingTelegramBot bot = TestBotUtils.newBot("MyBot");
        ConversationManager manager = ConversationManager.newConversationManager(bot);

        // We need to ensure the conversation body has reached waitForUpdate before the event fires
        CountDownLatch waiting = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(1);
        AtomicReference<Update> received = new AtomicReference<>();

        manager.registerConversation(Conversation.ofBuilder("flow", ctx -> {
            waiting.countDown();
            received.set(ctx.waitForUpdate(2, TimeUnit.SECONDS));
            done.countDown();
        }).build());

        manager.joinConversation(TestBotUtils.newUser(1L), TestBotUtils.newChat("1"), "flow");

        // Wait until the conversation thread is actually blocking on waitForUpdate
        assertTrue(waiting.await(2, TimeUnit.SECONDS), "Conversation never reached waitForUpdate");

        Update update = Update.parseResponse("{\"update_id\":12345}");
        bot.getEventManager().call(new UpdateReceivedEvent(bot, update, "{\"update_id\":12345}")).join();

        assertTrue(done.await(2, TimeUnit.SECONDS));
        assertNotNull(received.get());
        assertEquals(12345, received.get().updateId());
    }


    // ----------------------- utils

    private static ConversationManager newManager() {
        return ConversationManager.newConversationManager(TestBotUtils.newBot("MyBot"));
    }

    /** A conversation that stays alive until the latch is counted down. */
    private static Conversation conversation(String name,
                                             List<ConversationInstanceConstraint> constraints,
                                             CountDownLatch latch) {
        return Conversation.ofBuilder(name, _ -> await(latch))
                .instanceConstraints(constraints)
                .build();
    }

    private static Conversation blocking(String name) {
        return conversation(name, List.of(), new CountDownLatch(0));
    }

    private static void await(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

}
