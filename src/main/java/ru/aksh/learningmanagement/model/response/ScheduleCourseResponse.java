package ru.aksh.learningmanagement.model.response;

import java.time.LocalDateTime;

public record ScheduleCourseResponse(Long id, CourseResponse course, LocalDateTime courseDateTime) {
}
