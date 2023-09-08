package ru.gb.catalogservice.exceptions;

public class NoDataException extends RuntimeException{
    public NoDataException(String message) {
        super(message);
    }
}
