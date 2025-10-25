package ru.aksh.learningmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import ru.aksh.learningmanagement.domain.Schedule;
import ru.aksh.learningmanagement.model.request.ScheduleRequest;
import ru.aksh.learningmanagement.model.response.ScheduleBaseResponse;
import ru.aksh.learningmanagement.model.response.ScheduleCourseResponse;
import ru.aksh.learningmanagement.model.response.ScheduleTeacherResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        TeacherMapper.class, GroupMapper.class
})
public interface ScheduleMapper {
    ScheduleBaseResponse toScheduleBaseResponse(Schedule schedule);

    ScheduleCourseResponse toScheduleCourseResponse(Schedule schedule);

    ScheduleTeacherResponse toTeacherScheduleResponse(Schedule scheduleById);

    default Page<ScheduleBaseResponse> toScheduleResponsePage(Page<Schedule> schedules) {
        return schedules.map(this::toScheduleBaseResponse);
    }

    @Mapping(target = "id", ignore = true)
    void updateScheduleRequest(@MappingTarget Schedule schedule, ScheduleRequest request);
}
