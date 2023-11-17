package org.teleight.teleightbots.commands.builder.argument;

import org.teleight.teleightbots.commands.builder.exception.ArgumentSyntaxException;

public abstract class Argument<T> {

    protected final String id;

    public Argument(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract T parse(String input) throws ArgumentSyntaxException;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Argument<?> argument = (Argument<?>) o;

        return id.equals(argument.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
