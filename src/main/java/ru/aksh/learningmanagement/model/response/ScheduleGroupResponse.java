package ru.aksh.learningmanagement.model.response;

import java.util.List;

public record ScheduleGroupResponse(GroupResponse group, List<ScheduleCourseResponse> schedules) {
}
