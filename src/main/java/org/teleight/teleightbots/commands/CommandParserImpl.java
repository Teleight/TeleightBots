package org.teleight.teleightbots.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.api.objects.Message;
import org.teleight.teleightbots.api.objects.User;
import org.teleight.teleightbots.bot.Bot;
import org.teleight.teleightbots.commands.builder.Command;
import org.teleight.teleightbots.commands.builder.CommandExecutor;
import org.teleight.teleightbots.commands.builder.CommandSyntax;
import org.teleight.teleightbots.commands.builder.argument.Argument;
import org.teleight.teleightbots.commands.builder.argument.ArgumentString;
import org.teleight.teleightbots.commands.builder.condition.CommandCondition;
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
    public Result parse(Bot bot, @NotNull User sender, @NotNull String userInput, Message message) {
        final String commandAsString = commandManager.extractCommand(userInput);
        if (commandAsString == null) {
            return InvalidCommand.INSTANCE;
        }
        final String inputArguments = userInput.substring(userInput.indexOf(" ") + 1).replace("/" + commandAsString, "");
        final Command command = commandManager.getCommand(commandAsString);

        if (command == null) {
            return InvalidCommand.INSTANCE;
        }
        final CommandCondition defaultCondition = command.getCondition();

        final boolean hasCondition = defaultCondition != null;
        final boolean canUseThatCommand = !hasCondition || defaultCondition.canUse(bot, sender, message);
        if (!canUseThatCommand) {
            return InvalidCommand.INSTANCE;
        }


        final Chain chain = new Chain(bot, command, sender, inputArguments, message);
        parseNodes(chain);

        final CommandParserImpl.SyntaxResult bestSyntax = findBestSyntax(chain);
        if (bestSyntax != null && bestSyntax.isSuccessful()) {
            return ValidCommand.executor(bot, message, userInput, bestSyntax);
        }

        if (command.getDefaultExecutor() != null) {
            return ValidCommand.defaultExecutor(bot, message, userInput, command.getDefaultExecutor());
        }

        return InvalidCommand.INSTANCE;
    }

    private SyntaxResult findBestSyntax(@NotNull Chain chain) {
        for (final SyntaxResult syntaxResult : chain.syntaxesResults) {
            final CommandSyntax syntax = syntaxResult.syntax;
            final CommandCondition condition = syntax.getCondition();

            final boolean hasCondition = condition != null;
            if(hasCondition && !condition.canUse(chain.bot, chain.sender, chain.message)){
                continue;
            }
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

        final boolean hasEmptyArguments = syntaxArgs.length == 0;
        if(hasEmptyArguments){
            return syntaxResult;
        }

        for (Argument<?> argument : syntaxArgs) {
            if (!argumentReader.hasRemaining()) {
                syntaxResult.add(new ArgumentResult.IncompatibleType<>(argument.getId()));
                continue;
            }

            try {
                String input;
                if (argument instanceof ArgumentString argumentString) {
                    boolean canReadRemaining = argumentString.readRemaining();
                    if (canReadRemaining) {
                        input = argumentReader.readRemaining();
                    } else {
                        input = argumentReader.readWord();
                    }
                } else {
                    input = argumentReader.readWord();
                }
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
        private final Bot bot;
        private final Command command;
        private final User sender;
        private final String userInput;
        private final Message message;
        private final List<SyntaxResult> syntaxesResults = new ArrayList<>();

        private Chain(@NotNull Bot bot, @NotNull Command command, @NotNull User sender, @NotNull String userInput, @NotNull Message message) {
            this.bot = bot;
            this.command = command;
            this.sender = sender;
            this.userInput = userInput;
            this.message = message;
        }
    }

    private static class SyntaxResult {
        private final CommandSyntax syntax;
        private final List<ArgumentResult<?>> argumentResults = new ArrayList<>();

        private SyntaxResult(@NotNull CommandSyntax syntax) {
            this.syntax = syntax;
        }

        public boolean isSuccessful() {
            if(argumentResults.isEmpty()){
                return true;
            }
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

    private record ValidCommand(
            @NotNull Bot bot,
            @NotNull Message message,
            @NotNull String userInput,
            @Nullable List<ArgumentResult<?>> argumentResults,
            @NotNull CommandExecutor executor) implements Result {

        public static Result executor(@NotNull Bot bot,@NotNull Message message, @NotNull String userInput, @NotNull SyntaxResult bestSyntax) {
            return new ValidCommand(bot, message, userInput, bestSyntax.argumentResults, bestSyntax.syntax.getExecutor());
        }

        public static Result defaultExecutor(@NotNull Bot bot, @NotNull Message message,@NotNull String userInput, @NotNull CommandExecutor executor) {
            return new ValidCommand(bot, message, userInput, null, executor);
        }

        @Override
        public @NotNull ExecutableCommand executable() {
            return from -> {
                CommandContext commandContext = new CommandContext(bot, message, userInput);

                if (argumentResults != null) {
                    for (ArgumentResult<?> argumentResult : argumentResults) {
                        if (!(argumentResult instanceof ArgumentResult.Success<?> success)) {
                            continue;
                        }
                        commandContext.setArgument(success.argumentId(), success.value);
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
