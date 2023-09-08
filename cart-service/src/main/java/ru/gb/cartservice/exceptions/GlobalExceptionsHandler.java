package ru.gb.cartservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gb.api.dtos.dto.AppError;

@ControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> handleMailException(DuplicateInCartException e){
        return new ResponseEntity<>(new AppError("SEND_MAIL_ERROR",e.getMessage()), HttpStatus.BAD_REQUEST);
    }


}
