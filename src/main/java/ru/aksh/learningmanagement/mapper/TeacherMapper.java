package ru.aksh.learningmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.aksh.learningmanagement.domain.Teacher;
import ru.aksh.learningmanagement.model.response.TeacherResponse;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeacherMapper {
    TeacherResponse toTeacherResponse(Teacher teacher);

    List<TeacherResponse> toTeacherResponseList(List<Teacher> teachers);

    @Mapping(target = "id", ignore = true)
    void updateTeacherResponse(@MappingTarget Teacher teacher, Teacher updateTeacherDetails);
}
