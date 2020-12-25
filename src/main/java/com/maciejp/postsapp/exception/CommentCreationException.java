package com.maciejp.postsapp.exception;

public class CommentCreationException extends Throwable {

    private final String message;

    public CommentCreationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
