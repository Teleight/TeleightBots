package org.teleight.teleightbots.demo.command;

import org.teleight.teleightbots.commands.builder.Command;

public class Test2Command extends Command {
    public Test2Command() {
        super("test2");

        setDefaultExecutor((sender, context) -> {
            String input = context.getInput();

            System.out.println("test2 command executed: from: " + sender.username() + ", input: " + input);

            if (context.bot().getConversationManager().isUserInConversation(sender.id(), "test")) {
                System.out.println("User is already in conversation");
                return;
            }
            context.bot().getConversationManager().joinConversation(sender.id(), "test");
        });
    }
}
