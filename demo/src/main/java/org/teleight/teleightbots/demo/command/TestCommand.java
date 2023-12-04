package org.teleight.teleightbots.demo.command;

import org.teleight.teleightbots.commands.builder.Command;
import org.teleight.teleightbots.commands.builder.argument.ArgumentInteger;
import org.teleight.teleightbots.commands.builder.argument.ArgumentString;

public class TestCommand extends Command {

    public TestCommand() {
        super("test", "test1");


        setDefaultExecutor((sender, context) -> {
            String input = context.getInput();

            System.out.println("test command executed: from: " + sender.username() + ", input: " + input);
        });


        ArgumentInteger argumentInteger = new ArgumentInteger("int1");
        ArgumentInteger argumentInteger2 = new ArgumentInteger("int2");
        ArgumentString argumentString1 = new ArgumentString("string1");

        addSyntax((sender, context) -> {
            int value = context.getArgument(argumentInteger);
            int value2 = context.getArgument(argumentInteger2);
            System.out.println("syntax 1: first: " + value + ", second: " + value2);
        }, argumentInteger, argumentInteger2);

        addSyntax((sender, context) -> {
            int value = context.getArgument(argumentInteger);
            String value2 = context.getArgument(argumentString1);
            System.out.println("syntax 2: first: " + value + ", second: " + value2);
        }, argumentInteger, argumentString1);


        addSyntax((sender, context) -> {
            int value = context.getArgument(argumentInteger);
            System.out.println("syntax 3: value: " + value);
        }, argumentInteger);
    }

}
