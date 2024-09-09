package org.teleight.teleightbots.conversation;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a custom property that can be used in a conversation.
 *
 * @param <T>      the type of the property value.
 * @param name     the name of the property. Cannot be null.
 * @param value    the value of the property.
 * @param required whether the property is required or not.
 * @see Property#of(String)
 * @see Property#of(String, Object)
 */
public record Property<T>(
        @NotNull String name,
        T value,
        boolean required
) {

    /**
     * Creates a new property with the given name.
     * The returned property will have a null value and will be required.
     *
     * @param name the name of the property. Cannot be null.
     * @return a new property with the given name.
     */
    public static Property<Integer> of(@NotNull String name) {
        return new Property<>(name, null, true);
    }

    /**
     * Creates a new property with the given name and value.
     *
     * @param name  the name of the property. Cannot be null.
     * @param value the value of the property.
     * @return a new property with the given name and value.
     */
    public static <T> Property<T> of(String name, T value) {
        return new Property<>(name, value, false);
    }

    /**
     * Returns the value of the property as a string.
     *
     * @return the value of the property as a string.
     */
    public String asString() {
        return value.toString();
    }

    /**
     * Returns the value of the property as an integer.
     *
     * @return the value of the property as an integer.
     * @throws ClassCastException if the value cannot be cast to an integer.
     */
    public int asInt() {
        return (int) value;
    }

    /**
     * Returns the value of the property as a boolean.
     *
     * @return the value of the property as a boolean.
     * @throws ClassCastException if the value cannot be cast to a boolean.
     */
    public boolean asBool() {
        return (boolean) value;
    }

    /**
     * Returns the value of the property as a long.
     *
     * @return the value of the property as a long.
     * @throws ClassCastException if the value cannot be cast to a long.
     */
    public long asLong() {
        return (long) value;
    }

    /**
     * Returns the value of the property as a double.
     *
     * @return the value of the property as a double.
     * @throws ClassCastException if the value cannot be cast to a double.
     */
    public float asFloat() {
        return (float) value;
    }

    /**
     * Returns the value of the property as a custom type.
     *
     * @return the value of the property as a custom type.
     * @throws ClassCastException if the value cannot be cast to a custom type.
     */
    public <A> A as(Class<? extends A> type) {
        return type.cast(value);
    }

}
