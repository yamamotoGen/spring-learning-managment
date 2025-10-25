package ru.aksh.learningmanagement.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.aksh.learningmanagement.domain.Course;
import ru.aksh.learningmanagement.domain.Teacher;
import ru.aksh.learningmanagement.exception.CourseNotFoundException;
import ru.aksh.learningmanagement.exception.TeacherNotFoundException;
import ru.aksh.learningmanagement.mapper.CourseMapper;
import ru.aksh.learningmanagement.model.request.CourseRequest;
import ru.aksh.learningmanagement.model.response.CourseResponse;
import ru.aksh.learningmanagement.repository.CourseRepository;
import ru.aksh.learningmanagement.repository.TeacherRepository;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseMapper courseMapper;

    @Override
    public CourseResponse createCourse(CourseRequest request) {
        Teacher teacher = getTeacherById(request.getTeacherId());
        Course course = courseRepository.save(new Course(request.getCourseName(), teacher));
        return courseMapper.toCourseResponse(course);
    }

    @Override
    public Page<CourseResponse> findAll(Pageable pageable) {
        Page<Course> courses = courseRepository.findAll(pageable);
        return courseMapper.toCourseResponsePage(courses);
    }

    @Override
    public CourseResponse findById(Long id) {
        return courseMapper.toCourseResponse(getCourseById(id));
    }

    @Override
    public void updateCourse(Long id, CourseRequest request) {
        Teacher teacher = getTeacherById(request.getTeacherId());
        Course course = getCourseById(id);

        courseMapper.updateCourseRequest(course, request);
        course.setTeacher(teacher);
        courseRepository.save(course);
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.delete(getCourseById(id));
    }

    private Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course id " + id + " is not found"));
    }

    private Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher id " + id + " is not found"));
    }
}
