package ru.aksh.learningmanagement.model.response;

import java.time.LocalDateTime;

public record ScheduleBaseResponse(Long id, CourseResponse course,
                                   GroupResponse group, LocalDateTime courseDateTime) {
}
