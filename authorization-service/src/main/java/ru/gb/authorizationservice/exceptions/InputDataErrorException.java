package ru.gb.authorizationservice.exceptions;

import javax.validation.ValidationException;

public class InputDataErrorException extends ValidationException {
    public InputDataErrorException(String validationMessage) {
        super(validationMessage);
    }
}
