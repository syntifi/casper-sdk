package com.casper.sdk.exception;

import lombok.NoArgsConstructor;

/**
 * Thrown in case of a CLType which does not exist being requested
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@NoArgsConstructor
public class NoSuchTypeException extends Exception {
    public NoSuchTypeException(final String message) {
        super(message);
    }

    public NoSuchTypeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
