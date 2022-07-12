package com.casper.sdk.exception;

import lombok.NoArgsConstructor;

/**
 * Thrown when CLValue could not be decoded
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@NoArgsConstructor
public class CLValueDecodeException extends Exception {
    public CLValueDecodeException(final String message) {
        super(message);
    }

    public CLValueDecodeException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
