package ru.aksh.learningmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import ru.aksh.learningmanagement.domain.Teacher;
import ru.aksh.learningmanagement.model.request.TeacherRequest;
import ru.aksh.learningmanagement.model.response.TeacherResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeacherMapper {
    TeacherResponse toTeacherResponse(Teacher teacher);

    default Page<TeacherResponse> toTeacherResponsePage(Page<Teacher> teachers) {
        return teachers.map(this::toTeacherResponse);
    }

    @Mapping(target = "id", ignore = true)
    void updateTeacherRequest(@MappingTarget Teacher teacher, TeacherRequest request);
}
