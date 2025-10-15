package ru.aksh.learningmanagement.service;

import ru.aksh.learningmanagement.domain.Teacher;
import ru.aksh.learningmanagement.model.response.TeacherResponse;

import java.util.List;

public interface TeacherService {
    TeacherResponse createTeacher(String firstName, String lastName);

    TeacherResponse findById(Long id);

    List<TeacherResponse> findAll();

    void updateTeacher(Long id, Teacher updateTeacherDetails);

    void deleteById(Long id);
}
