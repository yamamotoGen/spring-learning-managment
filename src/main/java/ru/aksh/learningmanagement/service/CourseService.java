package ru.aksh.learningmanagement.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.aksh.learningmanagement.model.request.CourseRequest;
import ru.aksh.learningmanagement.model.response.CourseResponse;

public interface CourseService {
    CourseResponse createCourse(@Valid CourseRequest request);

    Page<CourseResponse> findAll(Pageable pageable);

    CourseResponse findById(Long id);

    void updateCourse(Long id, @Valid CourseRequest request);

    void deleteById(Long id);
}
