package ru.aksh.learningmanagement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.aksh.learningmanagement.model.request.TeacherRequest;
import ru.aksh.learningmanagement.model.response.TeacherResponse;

public interface TeacherService {
    TeacherResponse createTeacher(TeacherRequest request);

    TeacherResponse findById(Long id);

    Page<TeacherResponse> findAll(Pageable pageable);

    void updateTeacher(Long id, TeacherRequest request);

    void deleteById(Long id);
}
