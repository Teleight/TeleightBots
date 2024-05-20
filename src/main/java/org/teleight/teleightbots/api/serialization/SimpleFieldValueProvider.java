package org.teleight.teleightbots.api.serialization;

/**
 * Interface that should be implemented by enums that provide only field value information.
 */
public interface SimpleFieldValueProvider {

    /**
     * Gets the field value associated with the enum constant.
     *
     * @return the field value as a String
     */
    String getFieldValue();
}
