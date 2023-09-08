package ru.gb.cartservice.exceptions;

public class DuplicateInCartException extends RuntimeException {
    public DuplicateInCartException(String s)  {
        super(s);
    }
}
