package ru.gb.cartservice.exceptions;

public class FilmServiceIntegrationException extends RuntimeException {
    public FilmServiceIntegrationException(String message) {
        super(message);
    }
}
