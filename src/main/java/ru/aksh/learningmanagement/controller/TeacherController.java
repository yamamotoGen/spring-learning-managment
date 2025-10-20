package ru.aksh.learningmanagement.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.aksh.learningmanagement.model.request.TeacherRequest;
import ru.aksh.learningmanagement.model.response.TeacherResponse;
import ru.aksh.learningmanagement.service.TeacherService;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @PostMapping("/create")
    public TeacherResponse createTeacher(@Valid @RequestBody TeacherRequest request) {
        return teacherService.createTeacher(request);
    }

    @GetMapping("/all")
    public Page<TeacherResponse> findAllTeachers(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "20") @Min(1) @Max(100) Integer size,
            @RequestParam(value = "sort", defaultValue = "id") String sort) {
        return teacherService.findAll(PageRequest.of(page, size, Sort.by(sort)));
    }

    @GetMapping("/{id}")
    public TeacherResponse findById(@PathVariable Long id) {
        return teacherService.findById(id);
    }

    @PutMapping("/update/{id}")
    public void updateTeacher(@PathVariable Long id, @Valid @RequestBody TeacherRequest request) {
        teacherService.updateTeacher(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteById(id);
    }
}
