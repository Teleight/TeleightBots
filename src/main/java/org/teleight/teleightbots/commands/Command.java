package org.teleight.teleightbots.commands;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Command {

    private final String name;
    private final String[] aliases;

    private CommandExecutor defaultExecutor;

    public Command(@NotNull String name) {
        this(name, new String[0]);
    }

    public Command(@NotNull String name, @NotNull String... aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    protected final void setDefaultExecutor(@NotNull CommandExecutor executor) {
        this.defaultExecutor = executor;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String[] getAliases() {
        return aliases;
    }

    public @Nullable CommandExecutor getDefaultExecutor() {
        return defaultExecutor;
    }

}
