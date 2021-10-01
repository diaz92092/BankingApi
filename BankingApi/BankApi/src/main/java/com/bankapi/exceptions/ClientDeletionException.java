package com.bankapi.exceptions;

public class ClientDeletionException extends Exception{
    public ClientDeletionException() {
        super();
    }

    public ClientDeletionException(String message) {
        super(message);
    }

    public ClientDeletionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientDeletionException(Throwable cause) {
        super(cause);
    }

    public ClientDeletionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
