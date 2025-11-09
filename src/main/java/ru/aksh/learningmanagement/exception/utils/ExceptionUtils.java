package ru.aksh.learningmanagement.exception.utils;

import jakarta.validation.ConstraintViolationException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExceptionUtils {
    public String createConstraintViolationMessage(ConstraintViolationException ex) {
        return ex.getMessage().substring(ex.getMessage().lastIndexOf(".") + 1);
    }
}
