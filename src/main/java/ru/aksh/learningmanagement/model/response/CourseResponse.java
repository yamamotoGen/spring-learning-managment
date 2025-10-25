package ru.aksh.learningmanagement.model.response;

public record CourseResponse(Long id, String courseName, String description, TeacherResponse teacher) {
}
