package ru.aksh.learningmanagement.exception;

public class GroupNotFoundException extends EntityNotFoundRuntimeException {
    public GroupNotFoundException(String s) {
        super(s);
    }
}
