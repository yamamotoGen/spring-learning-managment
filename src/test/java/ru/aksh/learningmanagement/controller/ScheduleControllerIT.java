package ru.aksh.learningmanagement.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import ru.aksh.learningmanagement.domain.Course;
import ru.aksh.learningmanagement.domain.Group;
import ru.aksh.learningmanagement.domain.Schedule;
import ru.aksh.learningmanagement.domain.Teacher;
import ru.aksh.learningmanagement.model.request.ScheduleRequest;
import ru.aksh.learningmanagement.model.response.ScheduleBaseResponse;
import ru.aksh.learningmanagement.repository.CourseRepository;
import ru.aksh.learningmanagement.repository.GroupRepository;
import ru.aksh.learningmanagement.repository.ScheduleRepository;
import ru.aksh.learningmanagement.repository.TeacherRepository;
import ru.aksh.learningmanagement.service.ScheduleService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ScheduleControllerIT extends AbstractTestIT {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        Group group = new Group("Test group");
        Teacher teacher = new Teacher("Иван", "Макаров");
        Course course = new Course("Физкультура", teacher);

        groupRepository.save(group);
        teacherRepository.save(teacher);
        courseRepository.save(course);
        scheduleRepository.save(new Schedule(course, group, LocalDateTime.now()));
    }

    @AfterEach
    void resetDb() {
        scheduleRepository.deleteAll();
        courseRepository.deleteAll();
        teacherRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    void createSchedule_shouldReturnStatus200_ifValidRequest() {
        Schedule schedule = getSchedule();
        ScheduleRequest request = new ScheduleRequest(
                schedule.getCourse().getId(),
                schedule.getGroup().getId(),
                schedule.getCourseDateTime()
        );

        ResponseEntity<ScheduleBaseResponse> response = restTemplate.postForEntity(
                "/api/v1/schedules/create", request, ScheduleBaseResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().course().courseName()).isEqualTo("Физкультура");
        assertThat(response.getBody().course().teacher().firstName()).isEqualTo("Иван");
        assertThat(response.getBody().course().teacher().lastName()).isEqualTo("Макаров");
        assertThat(response.getBody().group().groupName()).isEqualTo("Test group");
    }

    @Test
    void createSchedule_shouldReturnStatus400_ifInvalidRequest() {
        ScheduleRequest request = new ScheduleRequest();

        ResponseEntity<ScheduleBaseResponse> response = restTemplate.postForEntity(
                "/api/v1/schedules/create", request, ScheduleBaseResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void createSchedule_shouldReturnStatus400_ifNegativeId() {
        ScheduleRequest request = new ScheduleRequest();
        request.setCourseId(-1L);
        request.setGroupId(-1L);
        request.setCourseDateTime(LocalDateTime.now());

        ResponseEntity<ScheduleBaseResponse> response = restTemplate.postForEntity(
                "/api/v1/schedules/create", request, ScheduleBaseResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void findAllGroupSchedule_shouldReturnStatus200_ifValidPageParameters() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "/api/v1/schedules/groups/all?page=0&size=10&sort=courseDateTime", Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = response.getBody();
        assertThat(body).isNotNull().containsEntry("number", 0)
                .containsEntry("size", 10)
                .containsKey("sort");
    }

    @Test
    void findAllGroupSchedule_shouldReturnStatus400_ifInvalidPageParameters() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "/api/v1/schedules/groups/all?page=0&size=200", Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Transactional
    void findAllGroupSchedule_shouldReturnCorrectData_ifValidPageParameters() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<ScheduleBaseResponse> allSchedule = scheduleService.findAllSchedule(pageRequest);

        assertThat(allSchedule).isNotNull();
        assertThat(allSchedule.getContent()).isNotEmpty();
        assertThat(allSchedule.getContent().get(0).course().courseName()).isEqualTo("Физкультура");
        assertThat(allSchedule.getContent().get(0).course().teacher().firstName()).isEqualTo("Иван");
        assertThat(allSchedule.getContent().get(0).course().teacher().lastName()).isEqualTo("Макаров");
        assertThat(allSchedule.getContent().get(0).group().groupName()).isEqualTo("Test group");
    }

    @Test
    void findScheduleByGroupId_ShouldReturnStatus200_ifValidPageParameters() {
        Schedule schedule = getSchedule();
        Long groupId = schedule.getGroup().getId();

        ResponseEntity<Map> response = restTemplate.getForEntity(
                "/api/v1/schedules/groups/{groupId}?page=0&size=10&sort=courseDateTime", Map.class, groupId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Map<String, Object> body = response.getBody();
        assertThat(body).isNotNull().containsKey("group").containsKey("schedules");

        Map<String, Object> group = (Map<String, Object>) body.get("group");
        assertThat(group).containsEntry("id", groupId.intValue()).containsEntry("groupName", "Test group");

        Map<String, Object> schedules = (Map<String, Object>) body.get("schedules");
        List<Map<String, Object>> content = (List<Map<String, Object>>) schedules.get("content");
        Map<String, Object> firstSchedule = content.get(0);

        Map<String, Object> course = (Map<String, Object>) firstSchedule.get("course");
        assertThat(course).containsEntry("courseName", "Физкультура");

        Map<String, Object> teacher = (Map<String, Object>) course.get("teacher");
        assertThat(teacher).containsEntry("firstName", "Иван").containsEntry("lastName", "Макаров");
    }

    @Test
    void findScheduleByGroupId_ShouldReturnStatus404_ifGroupNotExist() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "/api/v1/schedules/groups/{groupId}?page=0&size=10", Map.class, 500L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void findScheduleByGroupId_ShouldReturnStatus400_ifNegativeId() {
        ResponseEntity<Map> response = restTemplate.getForEntity(
                "/api/v1/schedules/groups/{groupId}?page=0&size=10", Map.class, -1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateSchedule_ShouldReturnStatus200_ifValidRequest() {
        ResponseEntity<ScheduleBaseResponse> exchange = getResponseEntityForPutMethod(getSchedule().getId());

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(exchange.getBody()).isNotNull();
        assertThat(exchange.getBody().course().courseName()).isEqualTo("Алгебра");
        assertThat(exchange.getBody().course().teacher().firstName()).isEqualTo("Петр");
        assertThat(exchange.getBody().course().teacher().lastName()).isEqualTo("Петров");
        assertThat(exchange.getBody().group().groupName()).isEqualTo("New group");
    }

    @Test
    void updateSchedule_ShouldReturnStatus400_ifNegativeId() {
        ResponseEntity<ScheduleBaseResponse> exchange = getResponseEntityForPutMethod(-1L);

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateSchedule_ShouldReturnStatus404_ifScheduleNotExist() {
        ResponseEntity<ScheduleBaseResponse> exchange = getResponseEntityForPutMethod(100L);

        assertThat(exchange.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteSchedule_ShouldReturnStatus200_IfValidId() {
        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/v1/schedules/{id}", HttpMethod.DELETE, null, Void.class, getSchedule().getId());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteSchedule_ShouldReturnStatus400_IfNegativeId() {
        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/v1/schedules/{id}", HttpMethod.DELETE, null, Void.class, -1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void deleteSchedule_ShouldReturnStatus400_IfInvalidId() {
        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/v1/schedules/{id}", HttpMethod.DELETE, null, Void.class, "qwerty");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private Schedule getSchedule() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        return scheduleList.get(0);
    }

    private ResponseEntity<ScheduleBaseResponse> getResponseEntityForPutMethod(Long id) {
        Group newGroup = new Group("New group");
        Teacher newTeacher = new Teacher("Петр", "Петров");
        Course newCourse = new Course("Алгебра", newTeacher);
        LocalDateTime courseDateTime = LocalDateTime.now().plusDays(2);

        groupRepository.save(newGroup);
        teacherRepository.save(newTeacher);
        courseRepository.save(newCourse);

        ScheduleRequest request = new ScheduleRequest(newCourse.getId(), newGroup.getId(), courseDateTime);
        HttpEntity<ScheduleRequest> entity = new HttpEntity<>(request);

        return restTemplate.exchange(
                "/api/v1/schedules/{id}", HttpMethod.PUT, entity, ScheduleBaseResponse.class, id);
    }
}