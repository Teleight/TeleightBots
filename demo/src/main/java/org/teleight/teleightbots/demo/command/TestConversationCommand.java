package org.teleight.teleightbots.demo.command;

import org.teleight.teleightbots.commands.builder.Command;
import org.teleight.teleightbots.conversation.ConversationManager;

import java.util.Map;

public class TestConversationCommand extends Command {

    public TestConversationCommand() {
        super("test_conversation");

        setDefaultExecutor((sender, context) -> {
            System.out.println(sender.username() + " want to start a conversation");

            final String conversationName = "test";

            if (context.bot().getConversationManager().isUserInConversation(sender)) {
                System.out.println("User is already in conversation");
                return;
            }

            Map<String, Object> properties = Map.of(
                    "test", "John",
                    "age", 25
            );
            ConversationManager.JoinResult result = context.bot().getConversationManager().joinConversation(
                    sender, context.message().chat(), conversationName, properties);
            if (result instanceof ConversationManager.JoinResult.AlreadyInConversation) {
                System.out.println("User is already in conversation");
            } else if (result instanceof ConversationManager.JoinResult.ConversationNotFound) {
                System.out.println("Conversation not found");
            } else {
                System.out.println("Conversation started");
            }
        });
    }

}
