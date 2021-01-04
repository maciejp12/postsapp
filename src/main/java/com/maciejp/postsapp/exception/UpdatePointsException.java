package com.maciejp.postsapp.exception;

public class UpdatePointsException extends Throwable {

    private final String message;

    public UpdatePointsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
