package ru.aksh.learningmanagement.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GroupRequest {

    @Size(min = 1)
    @NotEmpty
    private String groupName;
}
