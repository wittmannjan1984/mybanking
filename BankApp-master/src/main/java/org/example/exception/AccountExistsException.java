package org.example.exception;

public class AccountExistsException extends RuntimeException {

    public AccountExistsException(String message) {
        super(message);
    }
}