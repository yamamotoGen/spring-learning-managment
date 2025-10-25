package ru.aksh.learningmanagement.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleRequest {
    @NotNull
    @Positive
    private Long courseId;

    @NotNull
    @Positive
    private Long groupId;

    @NotNull
    private LocalDateTime courseDateTime;
}
