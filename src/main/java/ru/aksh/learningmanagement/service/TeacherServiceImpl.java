package ru.aksh.learningmanagement.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.aksh.learningmanagement.domain.Teacher;
import ru.aksh.learningmanagement.exception.TeacherNotFoundException;
import ru.aksh.learningmanagement.mapper.TeacherMapper;
import ru.aksh.learningmanagement.model.request.TeacherRequest;
import ru.aksh.learningmanagement.model.response.TeacherResponse;
import ru.aksh.learningmanagement.repository.TeacherRepository;

@Service
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public TeacherResponse createTeacher(TeacherRequest request) {
        Teacher teacher = teacherRepository.save(new Teacher(request.getFirstName(), request.getLastName()));
        return teacherMapper.toTeacherResponse(teacher);
    }

    @Override
    public TeacherResponse findById(Long id) {
        return teacherMapper.toTeacherResponse(getTeacherById(id));
    }

    @Override
    public Page<TeacherResponse> findAll(Pageable pageable) {
        Page<Teacher> teachers = teacherRepository.findAll(pageable);
        return teacherMapper.toTeacherResponsePage(teachers);
    }

    @Override
    public void updateTeacher(Long id, TeacherRequest request) {
        Teacher teacher = getTeacherById(id);

        teacherMapper.updateTeacherRequest(teacher, request);
        teacherRepository.save(teacher);
    }

    @Override
    public void deleteById(Long id) {
        teacherRepository.delete(getTeacherById(id));
    }

    private Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher id " + id + " is not found"));
    }
}
