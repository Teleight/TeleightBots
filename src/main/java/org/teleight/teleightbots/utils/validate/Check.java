package org.teleight.teleightbots.utils.validate;

/**
 * Class providing a utility method for state validation.
 * <p>
 * This class provides a static method to check a condition and throw an exception if the condition is true.
 */
public class Check {

    /**
     * Checks the provided condition and throws an IllegalStateException if the condition is true.
     *
     * @param condition the condition to be checked
     * @param reason    the message to be used for the exception if the condition is true
     * @throws IllegalStateException if the condition is true
     */
    public static void stateCondition(boolean condition, String reason) {
        if (condition) {
            throw new IllegalStateException(reason);
        }
    }

}
