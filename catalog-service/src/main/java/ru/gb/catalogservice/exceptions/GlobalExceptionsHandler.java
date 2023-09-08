package ru.gb.catalogservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gb.api.dtos.dto.AppError;

@ControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> handleResourceNotFoundException(ResourceNotFoundException e){
        return new ResponseEntity<>(new AppError("RESOURCE_NOT_FOUND",e.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    public ResponseEntity<AppError> handleIncorrectFilterParametrException(IncorrectFilterParametrException e){
        return new ResponseEntity<>(new AppError("INCORRECT_FILTER_PARAMETR",e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<AppError> handleIllegalInputDataException(IllegalInputDataException e){
        return new ResponseEntity<>(new AppError("ILLEGAL_INPUT_DATA",e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleIllegalNoData(NoDataException e){
        return new ResponseEntity<>(new AppError("NO_DATA",e.getMessage()), HttpStatus.CONFLICT);
    }
}
