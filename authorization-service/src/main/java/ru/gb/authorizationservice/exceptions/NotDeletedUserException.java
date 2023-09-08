package ru.gb.authorizationservice.exceptions;

public class NotDeletedUserException extends RuntimeException {
    public NotDeletedUserException(String s) {
        super(s);
    }
}
