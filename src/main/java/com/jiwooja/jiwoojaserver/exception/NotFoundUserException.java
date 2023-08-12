package com.jiwooja.jiwoojaserver.exception;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException() {
        super();
    }

    public NotFoundUserException(String message) {
        super(message);
    }

    public NotFoundUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundUserException(Throwable cause) {
        super(cause);
    }
}
