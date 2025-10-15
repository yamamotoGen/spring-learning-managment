package ru.aksh.learningmanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.aksh.learningmanagement.domain.Teacher;
import ru.aksh.learningmanagement.model.response.TeacherResponse;
import ru.aksh.learningmanagement.service.TeacherServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherServiceImpl teacherService;

    @PostMapping
    public TeacherResponse createTeacher(String firstName, String lastName) {
        return teacherService.createTeacher(firstName, lastName);
    }

    @GetMapping("/all")
    public List<TeacherResponse> findAllTeachers() {
        return teacherService.findAll();
    }

    @GetMapping("/{id}")
    public TeacherResponse findById(@PathVariable Long id) {
        return teacherService.findById(id);
    }

    @PutMapping("/update/{id}")
    public void updateTeacher(@PathVariable Long id, @RequestBody Teacher updateTeacherDetails) {
        teacherService.updateTeacher(id, updateTeacherDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteById(id);
    }
}
