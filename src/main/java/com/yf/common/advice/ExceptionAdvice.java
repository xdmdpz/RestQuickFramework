package com.yf.common.advice;

import com.yf.common.base.RestResponse;
import com.yf.common.exception.Exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExceptionAdvice {

    @ExceptionHandler(Exceptions.DataValidationFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResponse dataValidationFailedExceptionHandler(Exceptions.DataValidationFailedException ex) {
        String message = ex.getMessage();
        return (new RestResponse()).failed(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler(Exceptions.DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestResponse dataNotFoundExceptionHandler(Exceptions.DataNotFoundException ex) {
        String message = ex.getMessage();
        return (new RestResponse()).failed(HttpStatus.NOT_FOUND.value(), message);
    }

    @ExceptionHandler(Exceptions.DataConflictedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public RestResponse dataConflictedExceptionHandler(Exceptions.DataConflictedException ex) {
        String message = ex.getMessage();
        return (new RestResponse()).failed(HttpStatus.CONFLICT.value(), message);
    }

    @ExceptionHandler(Exceptions.UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RestResponse unauthorizedExceptionHandler(Exceptions.UnauthorizedException ex) {
        String message = ex.getMessage();
        return (new RestResponse()).failed(HttpStatus.UNAUTHORIZED.value(), message);
    }

    @ExceptionHandler(Exceptions.ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RestResponse forbiddenExceptionHandler(Exceptions.ForbiddenException ex ) {
        String message = ex.getMessage();
        return (new RestResponse()).failed(HttpStatus.FORBIDDEN.value(), message);
    }

}

