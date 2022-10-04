package com.justcodeit.moyeo.study.application.user.exception;

public class UserCannotFoundException extends RuntimeException {

    private static final String msg = "Cannot found User";

    public UserCannotFoundException() {
        super(msg);
    }
}
