package ru.aksh.learningmanagement.exception;

public class TeacherNotFoundException extends EntityNotFoundRuntimeException {
    public TeacherNotFoundException(String s) {
        super(s);
    }
}
