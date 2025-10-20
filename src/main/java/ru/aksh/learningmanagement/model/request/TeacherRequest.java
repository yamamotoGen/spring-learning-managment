package ru.aksh.learningmanagement.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TeacherRequest {
    @Size(min = 2)
    @NotEmpty
    private String firstName;

    @Size(min = 2)
    @NotEmpty
    private String lastName;
}
