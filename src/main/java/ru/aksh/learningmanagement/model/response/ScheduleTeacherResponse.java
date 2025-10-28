package ru.aksh.learningmanagement.model.response;

import org.springframework.data.domain.Page;

public record ScheduleTeacherResponse(TeacherResponse teacher, Page<ScheduleCourseResponse> schedules) {
}
