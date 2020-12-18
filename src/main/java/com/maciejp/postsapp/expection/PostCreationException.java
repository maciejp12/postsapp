package com.maciejp.postsapp.expection;

public class PostCreationException extends Throwable {

    private final String message;

    public PostCreationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
