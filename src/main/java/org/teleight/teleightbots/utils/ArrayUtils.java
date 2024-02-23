package org.teleight.teleightbots.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * A utility class for arrays.
 */
public interface ArrayUtils {

    /**
     * Adds a value to the end of an array.
     *
     * @param src   the source array
     * @param value the value to add
     * @param <T>   the type of the array
     * @return a new array with the value added
     */
    static <T> @NotNull T[] add(@NotNull T[] src, @NotNull T value) {
        final int length = src.length;
        final T[] newArray = Arrays.copyOf(src, length + 1);
        newArray[length] = value;
        return newArray;
    }

}
