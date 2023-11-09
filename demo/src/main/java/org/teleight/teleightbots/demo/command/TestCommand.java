package org.teleight.teleightbots.demo.command;

import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.commands.Command;

public class TestCommand extends Command {

    public TestCommand() {
        super("test", "test1");

        setDefaultExecutor(context -> {
            String input = context.getInput();
            User sender = context.getSender();

            System.out.println("test command executed: from: " + sender.username() + ", input: " + input);
        });
    }

}
