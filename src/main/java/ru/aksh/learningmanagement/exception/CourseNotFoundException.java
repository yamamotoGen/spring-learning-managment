package ru.aksh.learningmanagement.exception;

public class CourseNotFoundException extends EntityNotFoundRuntimeException {
    public CourseNotFoundException(String s) {
        super(s);
    }
}
