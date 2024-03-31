package org.teleight.teleightbots.utils;

public interface PropertyUtils {

    static Integer getInteger(String key, int defaultValue) {
        final var env = System.getenv(key);
        if (env != null) {
            try {
                return Integer.parseInt(env);
            } catch (NumberFormatException ignored) {
            }
        }
        return defaultValue;
    }

}
