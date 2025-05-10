package com.ftp.api.exceptions.UserException;

public class UserCreationException extends RuntimeException {
    public UserCreationException(String message) {
        super(message);
    }
}