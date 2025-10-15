package ru.aksh.learningmanagement.exception;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(String s) {
        super(s);
    }
}
