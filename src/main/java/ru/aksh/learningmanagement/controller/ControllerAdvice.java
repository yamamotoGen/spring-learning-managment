package ru.aksh.learningmanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.aksh.learningmanagement.exception.EntityNotFoundRuntimeException;
import ru.aksh.learningmanagement.model.response.exception.ExceptionResponse;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundRuntimeException.class)
    private ExceptionResponse notFound(EntityNotFoundRuntimeException ex) {
        return new ExceptionResponse(ex.getMessage());
    }
}
