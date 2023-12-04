package org.teleight.teleightbots.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.commands.builder.Command;
import org.teleight.teleightbots.commands.builder.CommandExecutor;
import org.teleight.teleightbots.commands.builder.CommandSyntax;
import org.teleight.teleightbots.commands.builder.argument.Argument;
import org.teleight.teleightbots.commands.builder.exception.ArgumentSyntaxException;
import org.teleight.teleightbots.commands.builder.parser.ArgumentReader;
import org.teleight.teleightbots.commands.builder.parser.CommandContext;

import java.util.ArrayList;
import java.util.List;

public final class CommandParserImpl implements CommandParser {

    private final CommandManager commandManager;

    CommandParserImpl(@NotNull CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    @Override
    public Result parse(@NotNull String userInput) {
        final String inputArguments = userInput.substring(userInput.indexOf(" ") + 1);

        final String commandAsString = commandManager.extractCommand(userInput);
        final Command command = commandManager.getCommand(commandAsString);

        if (command == null) {
            return InvalidCommand.INSTANCE;
        }

        final Chain chain = new Chain(command, inputArguments);
        parseNodes(chain);

        final CommandParserImpl.SyntaxResult bestSyntax = findBestSyntax(chain);
        if (bestSyntax != null && bestSyntax.isSuccessful()) {
            return ValidCommand.executor(userInput, bestSyntax);
        }

        if (command.getDefaultExecutor() != null) {
            return ValidCommand.defaultExecutor(userInput, command.getDefaultExecutor());
        }

        return InvalidCommand.INSTANCE;
    }

    private SyntaxResult findBestSyntax(@NotNull Chain chain) {
        for (SyntaxResult syntaxResult : chain.syntaxesResults) {
            if (syntaxResult.isSuccessful()) {
                return syntaxResult;
            }
        }
        return null;
    }

    private void parseNodes(@NotNull Chain chain) {
        final ArgumentReader argumentReader = new ArgumentReader(chain.userInput);
        final List<CommandSyntax> syntaxes = chain.command.getSyntaxes();
        for (CommandSyntax syntax : syntaxes) {
            final SyntaxResult syntaxResult = parseSyntax(syntax, argumentReader);
            argumentReader.resetCursor();
            chain.syntaxesResults.add(syntaxResult);
        }
    }

    private SyntaxResult parseSyntax(@NotNull CommandSyntax syntax, @NotNull ArgumentReader argumentReader) {
        final SyntaxResult syntaxResult = new SyntaxResult(syntax);
        final Argument<?>[] syntaxArgs = syntax.getArgs();
        for (Argument<?> argument : syntaxArgs) {
            if (!argumentReader.hasRemaining()) {
                syntaxResult.add(new ArgumentResult.IncompatibleType<>(argument.getId()));
                continue;
            }

            try {
                String input = argumentReader.readWord();
                Object result = argument.parse(input);

                syntaxResult.add(new ArgumentResult.Success<>(argument.getId(), result));
            } catch (ArgumentSyntaxException ignored) {
                syntaxResult.add(new ArgumentResult.IncompatibleType<>(argument.getId()));
            }
        }

        if (argumentReader.hasRemaining()) {
            syntaxResult.add(new ArgumentResult.SyntaxError<>("Command has fewer arguments passed"));
        }

        return syntaxResult;
    }

    private static class Chain {
        private final Command command;
        private final String userInput;
        private final List<SyntaxResult> syntaxesResults = new ArrayList<>();

        private Chain(@NotNull Command command, @NotNull String userInput) {
            this.command = command;
            this.userInput = userInput;
        }
    }

    private static class SyntaxResult {
        private final CommandSyntax syntax;
        private final List<ArgumentResult<?>> argumentResults = new ArrayList<>();

        private SyntaxResult(@NotNull CommandSyntax syntax) {
            this.syntax = syntax;
        }

        public boolean isSuccessful() {
            for (ArgumentResult<?> argumentResult : argumentResults) {
                if (!(argumentResult instanceof ArgumentResult.Success)) {
                    return false;
                }
            }
            return true;
        }

        public void add(@NotNull ArgumentResult<?> argumentResult) {
            argumentResults.add(argumentResult);
        }
    }

    private record InvalidCommand() implements Result {
        static final InvalidCommand INSTANCE = new InvalidCommand();

        @Override
        public @NotNull ExecutableCommand executable() {
            return from -> {};
        }
    }

    private record ValidCommand(@NotNull String userInput, @Nullable List<ArgumentResult<?>> argumentResults, @NotNull CommandExecutor executor) implements Result {

        public static Result executor(@NotNull String userInput, @NotNull SyntaxResult bestSyntax) {
            return new ValidCommand(userInput, bestSyntax.argumentResults, bestSyntax.syntax.getExecutor());
        }

        public static Result defaultExecutor(@NotNull String userInput, @NotNull CommandExecutor executor) {
            return new ValidCommand(userInput, null, executor);
        }

        @Override
        public @NotNull ExecutableCommand executable() {
            return from -> {
                CommandContext commandContext = new CommandContext(userInput);

                if(argumentResults != null) {
                    for (ArgumentResult<?> argumentResult : argumentResults) {
                        if (!(argumentResult instanceof ArgumentResult.Success<?> success)) {
                            continue;
                        }
                        commandContext.setArgument(success.argumentId(), success);
                    }
                }

                executor.execute(from, commandContext);
            };
        }
    }

    private sealed interface ArgumentResult<R> {
        record Success<T>(@NotNull String argumentId, @NotNull T value) implements ArgumentResult<T> {}

        record IncompatibleType<T>(@NotNull String argumentId) implements ArgumentResult<T> {}

        record SyntaxError<T>(@NotNull String message) implements ArgumentResult<T> {}
    }

}
