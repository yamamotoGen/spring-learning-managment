package ru.aksh.learningmanagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.aksh.learningmanagement.exception.TeacherNotFoundException;
import ru.aksh.learningmanagement.model.response.exception.ExceptionResponse;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TeacherNotFoundException.class)
    private ExceptionResponse notFound(TeacherNotFoundException ex) {
        return new ExceptionResponse(ex.getMessage());
    }
}
