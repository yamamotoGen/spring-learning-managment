package ru.aksh.learningmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import ru.aksh.learningmanagement.domain.Student;
import ru.aksh.learningmanagement.model.request.StudentRequest;
import ru.aksh.learningmanagement.model.response.StudentResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = GroupMapper.class)
public interface StudentMapper {
    StudentResponse toStudentResponse(Student student);

    default Page<StudentResponse> toStudentResponsePage(Page<Student> students) {
        return students.map(this::toStudentResponse);
    }

    @Mapping(target = "id", ignore = true)
    void updateStudentRequest(@MappingTarget Student student, StudentRequest request);
}
