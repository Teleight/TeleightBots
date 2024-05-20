package org.teleight.teleightbots.api.serialization;

/**
 * Interface that should be implemented by enums that provide field value and wrapper class information.
 *
 * @param <T> the type of the wrapper class
 */
public interface WrappedFieldValueProvider<T> extends SimpleFieldValueProvider {

    /**
     * Gets the wrapper class associated with the enum constant.
     *
     * @return the wrapper class
     */
    Class<? extends T> getWrapperClass();

    /**
     * Gets the field name associated with the enum constant.
     *
     * @return the field name as a String
     */
    String getFieldName();
}
