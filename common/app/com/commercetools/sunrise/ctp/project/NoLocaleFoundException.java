package com.commercetools.sunrise.ctp.project;

public class NoLocaleFoundException extends RuntimeException {

    public NoLocaleFoundException(final String message) {
        super(message);
    }

    public NoLocaleFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
