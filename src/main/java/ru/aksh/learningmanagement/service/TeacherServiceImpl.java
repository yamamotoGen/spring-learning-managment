package ru.aksh.learningmanagement.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.aksh.learningmanagement.domain.Teacher;
import ru.aksh.learningmanagement.exception.TeacherNotFoundException;
import ru.aksh.learningmanagement.mapper.TeacherMapper;
import ru.aksh.learningmanagement.model.response.TeacherResponse;
import ru.aksh.learningmanagement.repository.TeacherRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public TeacherResponse createTeacher(String firstName, String lastName) {
        Teacher teacher = teacherRepository.save(new Teacher(firstName, lastName));
        return teacherMapper.toTeacherResponse(teacher);
    }

    @Override
    public TeacherResponse findById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher id " + id + " is not found"));
        return teacherMapper.toTeacherResponse(teacher);
    }

    @Override
    public List<TeacherResponse> findAll() {
        List<Teacher> teachers = teacherRepository.findAll();
        return teacherMapper.toTeacherResponseList(teachers);
    }

    @Override
    public void updateTeacher(Long id, Teacher updateTeacherDetails) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher id " + id + " is not found"));
        teacherMapper.updateTeacherResponse(teacher, updateTeacherDetails);
        teacherRepository.save(teacher);
    }

    @Override
    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }
}
