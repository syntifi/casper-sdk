package com.casper.sdk.exception;

import lombok.NoArgsConstructor;

/**
 * Thrown when CLValue could not be encoded
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@NoArgsConstructor
public class CLValueEncodeException extends Exception {
    public CLValueEncodeException(final String message) {
        super(message);
    }

    public CLValueEncodeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
