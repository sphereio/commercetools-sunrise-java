package com.commercetools.sunrise.ctp.project;

public class NoCurrencyFoundException extends RuntimeException {

    public NoCurrencyFoundException(final String message) {
        super(message);
    }

    public NoCurrencyFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
