package com.bikkadit.elcetronicstore.exceptions;

public class BadApiException extends RuntimeException {

    public BadApiException() {
        super("Bad Request !!!");
    }

    public BadApiException(String message) {
        super(message);
    }
}
