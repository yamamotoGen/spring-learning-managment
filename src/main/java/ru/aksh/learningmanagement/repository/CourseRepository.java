package ru.aksh.learningmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aksh.learningmanagement.domain.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByTeacherId(Long teacherId);
}
