package ru.aksh.learningmanagement.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.aksh.learningmanagement.domain.Teacher;
import ru.aksh.learningmanagement.repository.TeacherRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

class TeacherControllerIT extends AbstractTestIT{
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void resetDb() {
        teacherRepository.deleteAll();
    }

    @Test
    void createTeacher_shouldReturnStatus200_ifValidRequest() {
        Teacher teacher = teacherRepository.save(new Teacher("Иван", "Сидоров"));

        ResponseEntity<Teacher> response = getResponseTeacherEntity(teacher);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getId(), notNullValue());
        assertThat(response.getBody().getFirstName(), is("Иван"));
        assertThat(response.getBody().getLastName(), is("Сидоров"));
    }

    @Test
    void createTeacher_shouldReturnStatus400_ifRequestIsNull() {
        HttpEntity<Teacher> request = new HttpEntity<>(null);

        ResponseEntity<Teacher> response = restTemplate.postForEntity(
                "/api/v1/teachers/create", request, Teacher.class);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void createTeacher_shouldReturnStatus400_ifRequestDataIsEmpty() {
        Teacher teacher = teacherRepository.save(new Teacher("", ""));

        ResponseEntity<Teacher> response = getResponseTeacherEntity(teacher);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void createTeacher_shouldReturnStatus400_ifRequestDataIsInvalidSize() {
        Teacher teacher = teacherRepository.save(new Teacher("A", "B"));

        ResponseEntity<Teacher> response = getResponseTeacherEntity(teacher);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    private ResponseEntity<Teacher> getResponseTeacherEntity(Teacher teacher) {
        return restTemplate.postForEntity("/api/v1/teachers/create", teacher, Teacher.class);
    }
}