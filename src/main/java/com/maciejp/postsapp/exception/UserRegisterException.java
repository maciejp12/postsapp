package com.maciejp.postsapp.exception;

public class UserRegisterException extends Throwable {

    private final String message;

    public UserRegisterException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
