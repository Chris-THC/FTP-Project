package com.ftp.api.exceptions.UserException;

public class UserDeletionException extends RuntimeException {
    public UserDeletionException(String message) {
        super(message);
    }
}