package org.teleight.teleightbots.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public interface ArrayUtils {

    static <T> @NotNull T[] add(@NotNull T[] src, @NotNull T value) {
        final int length = src.length;
        final T[] newArray = Arrays.copyOf(src, length + 1);
        newArray[length] = value;
        return newArray;
    }

}
