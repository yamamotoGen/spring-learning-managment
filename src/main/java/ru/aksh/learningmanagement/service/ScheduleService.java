package ru.aksh.learningmanagement.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.aksh.learningmanagement.model.request.ScheduleRequest;
import ru.aksh.learningmanagement.model.response.ScheduleBaseResponse;
import ru.aksh.learningmanagement.model.response.ScheduleGroupResponse;
import ru.aksh.learningmanagement.model.response.ScheduleTeacherResponse;

public interface ScheduleService {
    ScheduleBaseResponse createSchedule(@Valid ScheduleRequest request);

    Page<ScheduleBaseResponse> findAllSchedule(Pageable pageable);

    ScheduleGroupResponse findScheduleByGroupId(Long groupId, Pageable pageable);

    ScheduleTeacherResponse findScheduleByTeacherId(Long teacherId, Pageable pageable);

    void updateSchedule(Long id, @Valid ScheduleRequest request);

    void deleteById(Long id);
}
