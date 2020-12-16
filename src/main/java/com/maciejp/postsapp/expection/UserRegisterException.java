package com.maciejp.postsapp.expection;

public class UserRegisterException extends Throwable {

    private final String message;

    public UserRegisterException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
