package ru.gb.catalogservice.exceptions;

public class IncorrectFilterParametrException extends RuntimeException{
    public IncorrectFilterParametrException(String message) {
        super(message);
    }
}
