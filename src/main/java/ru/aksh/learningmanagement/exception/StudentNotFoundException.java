package ru.aksh.learningmanagement.exception;

public class StudentNotFoundException extends EntityNotFoundRuntimeException {
    public StudentNotFoundException(String s) {
        super(s);
    }
}
