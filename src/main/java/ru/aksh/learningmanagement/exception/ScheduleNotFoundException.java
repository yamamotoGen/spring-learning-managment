package ru.aksh.learningmanagement.exception;

public class ScheduleNotFoundException extends EntityNotFoundRuntimeException {
    public ScheduleNotFoundException(String s) {
        super(s);
    }
}
