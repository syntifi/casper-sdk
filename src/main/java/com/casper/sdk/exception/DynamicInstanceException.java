package com.casper.sdk.exception;

/**
 * Thrown when some error occurs while dynamically instantiating an object
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
public class DynamicInstanceException extends Exception {
    public DynamicInstanceException(final String message) {
        super(message);
    }

    public DynamicInstanceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
