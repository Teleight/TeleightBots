package org.teleight.teleightbots.commands.builder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.commands.builder.argument.Argument;
import org.teleight.teleightbots.commands.builder.condition.CommandCondition;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {

    private final String name;
    private final String[] aliases;

    private CommandExecutor defaultExecutor;
    private CommandCondition condition;

    private final List<CommandSyntax> syntaxes = new ArrayList<>();

    public Command(@NotNull String name) {
        this(name, new String[0]);
    }

    public Command(@NotNull String name, @NotNull String... aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    public @Nullable CommandExecutor getDefaultExecutor() {
        return defaultExecutor;
    }

    protected final void setDefaultExecutor(@NotNull CommandExecutor executor) {
        this.defaultExecutor = executor;
    }

    public @Nullable CommandCondition getCondition() {
        return condition;
    }

    protected void setCondition(@Nullable CommandCondition commandCondition) {
        this.condition = commandCondition;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String[] getAliases() {
        return aliases;
    }

    public void addSyntax(@NotNull CommandExecutor executor, @NotNull Argument<?>... args) {
        syntaxes.add(new CommandSyntax(null, executor, args));
    }

    public void addConditionalSyntax(@NotNull CommandCondition condition, @NotNull CommandExecutor executor, @NotNull Argument<?>... args) {
        syntaxes.add(new CommandSyntax(condition, executor, args));
    }

    public List<CommandSyntax> getSyntaxes() {
        return syntaxes;
    }

}
