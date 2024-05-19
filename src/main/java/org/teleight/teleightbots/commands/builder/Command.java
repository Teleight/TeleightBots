package org.teleight.teleightbots.commands.builder;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.teleight.teleightbots.commands.builder.argument.Argument;
import org.teleight.teleightbots.commands.builder.condition.CommandCondition;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a command with a name, aliases, and a set of syntaxes.
 * Commands can have a default executor and a condition to be checked before execution.
 *
 * <p>This abstract class serves as a base for creating specific commands.</p>
 */
public abstract class Command {

    private final String name;
    private final String[] aliases;

    private CommandExecutor defaultExecutor;
    private CommandCondition condition;

    private final List<CommandSyntax> syntaxes = new ArrayList<>();

    /**
     * Constructs a command with the given name.
     *
     * @param name the name of the command
     * @throws NullPointerException if the name is null
     */
    public Command(@NotNull String name) {
        this(name, new String[0]);
    }

    /**
     * Constructs a command with the given name and aliases.
     *
     * @param name    the name of the command
     * @param aliases the aliases of the command
     * @throws NullPointerException if the name or aliases are null
     */
    public Command(@NotNull String name, @NotNull String... aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    /**
     * Returns the default executor for this command.
     *
     * @return the default executor, or null if none is set
     */
    public @Nullable CommandExecutor getDefaultExecutor() {
        return defaultExecutor;
    }

    /**
     * Sets the default executor for this command.
     *
     * @param executor the executor to set as the default
     * @throws NullPointerException if the executor is null
     */
    protected final void setDefaultExecutor(@NotNull CommandExecutor executor) {
        this.defaultExecutor = executor;
    }

    /**
     * Returns the condition for this command.
     *
     * @return the condition, or null if none is set
     */
    public @Nullable CommandCondition getCondition() {
        return condition;
    }

    /**
     * Sets the condition for this command.
     *
     * @param commandCondition the condition to set
     */
    protected void setCondition(@Nullable CommandCondition commandCondition) {
        this.condition = commandCondition;
    }

    /**
     * Returns the name of this command.
     *
     * @return the name of the command
     */
    public @NotNull String getName() {
        return name;
    }

    /**
     * Returns the aliases of this command.
     *
     * @return an array of aliases
     */
    public @NotNull String[] getAliases() {
        return aliases;
    }

    /**
     * Adds a syntax to this command with the given executor and arguments.
     *
     * @param executor the executor for the syntax
     * @param args     the arguments for the syntax
     * @throws NullPointerException if the executor or args are null
     */
    public void addSyntax(@NotNull CommandExecutor executor, @NotNull Argument<?>... args) {
        syntaxes.add(new CommandSyntax(null, executor, args));
    }

    /**
     * Adds a conditional syntax to this command with the given condition, executor, and arguments.
     *
     * @param condition the condition for the syntax
     * @param executor  the executor for the syntax
     * @param args      the arguments for the syntax
     * @throws NullPointerException if the condition, executor, or args are null
     */
    public void addConditionalSyntax(@NotNull CommandCondition condition, @NotNull CommandExecutor executor, @NotNull Argument<?>... args) {
        syntaxes.add(new CommandSyntax(condition, executor, args));
    }

    /**
     * Returns the list of syntaxes for this command.
     *
     * @return the list of syntaxes
     */
    public List<CommandSyntax> getSyntaxes() {
        return syntaxes;
    }

}
