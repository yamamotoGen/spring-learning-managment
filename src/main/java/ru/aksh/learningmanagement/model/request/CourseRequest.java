package ru.aksh.learningmanagement.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CourseRequest {
    @Size(min = 1)
    @NotEmpty
    private String courseName;

    @NotNull
    @Positive
    private Long teacherId;
}
