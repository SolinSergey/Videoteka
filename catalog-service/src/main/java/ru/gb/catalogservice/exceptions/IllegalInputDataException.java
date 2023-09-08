package ru.gb.catalogservice.exceptions;

public class IllegalInputDataException extends RuntimeException{
    public IllegalInputDataException(String message) {
        super(message);
    }
}
