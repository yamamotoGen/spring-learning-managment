package ru.aksh.learningmanagement.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.aksh.learningmanagement.model.request.ScheduleRequest;
import ru.aksh.learningmanagement.model.response.ScheduleBaseResponse;
import ru.aksh.learningmanagement.model.response.ScheduleGroupResponse;
import ru.aksh.learningmanagement.model.response.ScheduleTeacherResponse;
import ru.aksh.learningmanagement.service.ScheduleService;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
@Validated
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/create")
    public ScheduleBaseResponse createSchedule(@Valid @RequestBody ScheduleRequest request) {
        return scheduleService.createSchedule(request);
    }

    @GetMapping("/groups/all")
    public Page<ScheduleBaseResponse> findAllGroupSchedule(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "20") @Min(1) @Max(100) Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort) {
        return scheduleService.findAllSchedule(PageRequest.of(page, size, Sort.by(sort)));
    }

    @GetMapping("/groups/{groupId}")
    public ScheduleGroupResponse findScheduleByGroupId(
            @PathVariable @NotNull @Positive Long groupId,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "20") @Min(1) @Max(100) Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort) {
        return scheduleService.findScheduleByGroupId(groupId, PageRequest.of(page, size, Sort.by(sort)));
    }

    @GetMapping("/teachers/{teacherId}")
    public ScheduleTeacherResponse findScheduleByTeacherId(
            @PathVariable @NotNull @Positive Long teacherId,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "20") @Min(1) @Max(100) Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort) {
        return scheduleService.findScheduleByTeacherId(teacherId, PageRequest.of(page, size, Sort.by(sort)));
    }

    @PutMapping("/{id}")
    public ScheduleBaseResponse updateSchedule(
            @PathVariable @NotNull @Positive Long id, @Valid @RequestBody ScheduleRequest request) {
        return scheduleService.updateSchedule(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable @NotNull @Positive Long id) {
        scheduleService.deleteById(id);
    }
}
