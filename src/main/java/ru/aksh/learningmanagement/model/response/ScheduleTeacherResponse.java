package ru.aksh.learningmanagement.model.response;

import java.util.List;

public record ScheduleTeacherResponse(TeacherResponse teacher, List<ScheduleCourseResponse> schedules) {
}
