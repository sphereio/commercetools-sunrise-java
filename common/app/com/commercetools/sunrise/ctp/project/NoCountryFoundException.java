package com.commercetools.sunrise.ctp.project;

public class NoCountryFoundException extends RuntimeException {

    public NoCountryFoundException(final String message) {
        super(message);
    }

    public NoCountryFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
