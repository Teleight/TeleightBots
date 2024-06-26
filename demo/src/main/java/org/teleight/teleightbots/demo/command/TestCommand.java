package org.teleight.teleightbots.demo.command;

import org.teleight.teleightbots.api.methods.SendMessage;
import org.teleight.teleightbots.commands.builder.Command;
import org.teleight.teleightbots.commands.builder.argument.ArgumentInteger;
import org.teleight.teleightbots.commands.builder.argument.ArgumentString;

public class TestCommand extends Command {

    public TestCommand() {
        super("test", "test1");

        setDefaultExecutor((sender, context) -> {
            final String message = "test command executed: from: " + sender.username() + ", input: " + context.getInput();
            System.out.println(message);

            context.bot().execute(SendMessage.ofBuilder(context.message().chatId(), message).build());
        });

        final ArgumentInteger argumentInteger = new ArgumentInteger("int1");
        final ArgumentInteger argumentInteger2 = new ArgumentInteger("int2");
        final ArgumentString argumentString1 = new ArgumentString("string1");

        addSyntax((sender, context) -> {
            int value = context.getArgument(argumentInteger);
            int value2 = context.getArgument(argumentInteger2);

            final String message = "syntax 1: first: " + value + ", second: " + value2;
            System.out.println(message);

            context.bot().execute(SendMessage.ofBuilder(context.message().chatId(), message).build());
        }, argumentInteger, argumentInteger2);

        addSyntax((sender, context) -> {
            int value = context.getArgument(argumentInteger);
            String value2 = context.getArgument(argumentString1);

            final String message = "syntax 2: first: " + value + ", second: " + value2;
            System.out.println(message);

            context.bot().execute(SendMessage.ofBuilder(context.message().chatId(), message).build());
        }, argumentInteger, argumentString1);

        addSyntax((sender, context) -> {
            int value = context.getArgument(argumentInteger);

            final String message = "syntax 3: value: " + value;
            System.out.println(message);

            context.bot().execute(SendMessage.ofBuilder(context.message().chatId(), message).build());
        }, argumentInteger);
    }

}
