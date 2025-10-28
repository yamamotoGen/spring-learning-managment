package ru.aksh.learningmanagement.model.response;

import org.springframework.data.domain.Page;

public record ScheduleGroupResponse(GroupResponse group, Page<ScheduleCourseResponse> schedules) {
}
