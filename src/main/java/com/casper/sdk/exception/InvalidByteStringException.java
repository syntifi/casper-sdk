package com.casper.sdk.exception;

import lombok.NoArgsConstructor;

/**
 * Thrown in case of an invalid (unparseable) encoded byte string
 *
 * @author Alexandre Carvalho
 * @author Andre Bertolace
 * @since 0.0.1
 */
@NoArgsConstructor
public class InvalidByteStringException extends CLValueDecodeException {
    public InvalidByteStringException(final String message) {
        super(message);
    }

    public InvalidByteStringException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
