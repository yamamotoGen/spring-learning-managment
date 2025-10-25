package ru.aksh.learningmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;
import ru.aksh.learningmanagement.domain.Course;
import ru.aksh.learningmanagement.model.request.CourseRequest;
import ru.aksh.learningmanagement.model.response.CourseResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = TeacherMapper.class)
public interface CourseMapper {
    CourseResponse toCourseResponse(Course course);

    default Page<CourseResponse> toCourseResponsePage(Page<Course> courses) {
        return courses.map(this::toCourseResponse);
    }

    @Mapping(target = "id", ignore = true)
    void updateCourseRequest(@MappingTarget Course course, CourseRequest request);
}
