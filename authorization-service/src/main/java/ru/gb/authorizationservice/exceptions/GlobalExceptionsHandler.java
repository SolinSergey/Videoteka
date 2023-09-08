package ru.gb.authorizationservice.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import ru.gb.api.dtos.dto.AppError;
import ru.gb.common.constants.InfoMessage;

@ControllerAdvice
public class GlobalExceptionsHandler implements InfoMessage {
    @ExceptionHandler
    public ResponseEntity<AppError> handleResourceNotFoundException(ResourceNotFoundException e){
        return new ResponseEntity<>(new AppError(RESOURCE_NOT_FOUND_CODE, e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleUsernameNotFoundException(UsernameNotFoundException e){
        return new ResponseEntity<>(new AppError(USERNAME_NOT_FOUND_CODE,e.getMessage()),
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<AppError> handleBadCredentialsException(BadCredentialsException e){
        return new ResponseEntity<>(
                new AppError(CHECK_USERNAME_PASSWORD_ERROR_CODE, INVALID_USERNAME_OR_PASSWORD),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleInputDataErrorException(InputDataErrorException e){
        return new ResponseEntity<>(new AppError(INPUT_DATA_ERROR_CODE,
                e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleNotDeletedUserException(NotDeletedUserException e){
        return new ResponseEntity<>(new AppError(USER_ALREADY_EXISTS_CODE,
                e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleExpiredJwtException(ExpiredJwtException e){
        return new ResponseEntity<>(new AppError(TOKEN_IS_EXPIRED_CODE,
                e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleMalformedJwtException(MalformedJwtException e){
        return new ResponseEntity<>(new AppError(TOKEN_IS_MALFORMED_CODE,
                e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleSignatureException(SignatureException e){
        return new ResponseEntity<>(new AppError(INVALID_SIGNATURE_CODE,
                e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleUnsupportedJwtException(UnsupportedJwtException e){
        return new ResponseEntity<>(new AppError(UNSUPPORTED_JWT_TOKEN_CODE,
                e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleIllegalArgumentException(IllegalArgumentException e){
        return new ResponseEntity<>(new AppError(ILLEGAL_ARGUMENT_CODE,
                e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handlePublicKeyErrorException(PublicKeyErrorException e){
        return new ResponseEntity<>(new AppError(PUBLIC_KEY_ERROR_CODE,
                e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleIntegrationException(IntegrationException e){
        return new ResponseEntity<>(new AppError(INTEGRATION_ERROR_CODE, e.getMessage()),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler
    public ResponseEntity<AppError> handleWebClientRequestException(WebClientRequestException e) {
        return new ResponseEntity<>(new AppError(INTEGRATION_ERROR_CODE, e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
