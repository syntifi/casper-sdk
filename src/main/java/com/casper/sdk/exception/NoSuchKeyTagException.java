package com.casper.sdk.exception;

import lombok.NoArgsConstructor;

/**
 * Thrown in case of a Key tag which does not exist being requested
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@NoArgsConstructor
public class NoSuchKeyTagException extends Exception {
    public NoSuchKeyTagException(final String message) {
        super(message);
    }

    public NoSuchKeyTagException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
