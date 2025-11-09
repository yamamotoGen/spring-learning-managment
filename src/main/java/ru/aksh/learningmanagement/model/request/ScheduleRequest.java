package ru.aksh.learningmanagement.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
