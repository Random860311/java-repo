package com.qa.lib.core.exception;

public class AppException extends RuntimeException {
    private final String userMessage;

    public AppException(String message, Throwable cause, String userMessage) {
        super(message, cause);
        this.userMessage = userMessage;
    }
    public AppException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }
}
