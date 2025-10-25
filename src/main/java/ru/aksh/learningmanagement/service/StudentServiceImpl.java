package ru.aksh.learningmanagement.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.aksh.learningmanagement.domain.Group;
import ru.aksh.learningmanagement.domain.Student;
import ru.aksh.learningmanagement.exception.GroupNotFoundException;
import ru.aksh.learningmanagement.exception.StudentNotFoundException;
import ru.aksh.learningmanagement.mapper.StudentMapper;
import ru.aksh.learningmanagement.model.request.StudentRequest;
import ru.aksh.learningmanagement.model.response.StudentResponse;
import ru.aksh.learningmanagement.repository.GroupRepository;
import ru.aksh.learningmanagement.repository.StudentRepository;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final StudentMapper studentMapper;

    @Override
    public StudentResponse createStudent(StudentRequest request) {
        Group group = getGroupById(request.getGroupId());
        Student student = studentRepository.save(new Student(request.getFirstName(), request.getLastName(), group));

        return studentMapper.toStudentResponse(student);
    }

    @Override
    public Page<StudentResponse> findAll(Pageable pageable) {
        Page<Student> students = studentRepository.findAll(pageable);
        return studentMapper.toStudentResponsePage(students);
    }

    @Override
    public StudentResponse findById(Long id) {
        return studentMapper.toStudentResponse(getStudentById(id));
    }

    @Override
    public void updateStudent(Long id, StudentRequest request) {
        Student student = getStudentById(id);
        Group group = getGroupById(request.getGroupId());

        studentMapper.updateStudentRequest(student, request);
        student.setGroup(group);
        studentRepository.save(student);
    }

    @Override
    public void deleteById(Long id) {
        studentRepository.delete(getStudentById(id));
    }

    private Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student id " + id + " is not found"));
    }

    private Group getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group id " + id + "is not found"));
    }
}
