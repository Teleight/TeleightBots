package org.teleight.teleightbots.demo.command;

import org.teleight.teleightbots.commands.builder.Command;

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

            context.bot().getConversationManager().joinConversation(sender, context.message().chat(), conversationName);
        });
    }

}
