package ru.aksh.learningmanagement.service;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.aksh.learningmanagement.model.request.StudentRequest;
import ru.aksh.learningmanagement.model.response.StudentResponse;

public interface StudentService {
    StudentResponse createStudent(@Valid StudentRequest request);

    Page<StudentResponse> findAll(Pageable pageable);

    StudentResponse findById(Long id);

    void updateStudent(Long id, @Valid StudentRequest request);

    void deleteById(Long id);
}
