package ru.aksh.learningmanagement.exception;

public class EntityNotFoundRuntimeException extends RuntimeException{
    public EntityNotFoundRuntimeException(String message) {
        super(message);
    }
}
