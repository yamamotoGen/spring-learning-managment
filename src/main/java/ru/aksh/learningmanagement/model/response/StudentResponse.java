package ru.aksh.learningmanagement.model.response;

public record StudentResponse(Long id, String firstName, String lastName, GroupResponse group) {
}
