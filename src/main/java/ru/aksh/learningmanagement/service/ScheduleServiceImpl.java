package ru.aksh.learningmanagement.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.aksh.learningmanagement.domain.Course;
import ru.aksh.learningmanagement.domain.Group;
import ru.aksh.learningmanagement.domain.Schedule;
import ru.aksh.learningmanagement.domain.Teacher;
import ru.aksh.learningmanagement.exception.CourseNotFoundException;
import ru.aksh.learningmanagement.exception.GroupNotFoundException;
import ru.aksh.learningmanagement.exception.ScheduleNotFoundException;
import ru.aksh.learningmanagement.exception.TeacherNotFoundException;
import ru.aksh.learningmanagement.mapper.GroupMapper;
import ru.aksh.learningmanagement.mapper.ScheduleMapper;
import ru.aksh.learningmanagement.mapper.TeacherMapper;
import ru.aksh.learningmanagement.model.request.ScheduleRequest;
import ru.aksh.learningmanagement.model.response.*;
import ru.aksh.learningmanagement.repository.CourseRepository;
import ru.aksh.learningmanagement.repository.GroupRepository;
import ru.aksh.learningmanagement.repository.ScheduleRepository;
import ru.aksh.learningmanagement.repository.TeacherRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    private final ScheduleMapper scheduleMapper;
    private final TeacherMapper teacherMapper;
    private final GroupMapper groupMapper;

    @Override
    public ScheduleBaseResponse createSchedule(ScheduleRequest request) {
        Course course = getCourseById(request.getCourseId());
        Group group = getGroupById(request.getGroupId());
        Schedule schedule = scheduleRepository.save(new Schedule(course, group, request.getCourseDateTime()));

        return scheduleMapper.toScheduleBaseResponse(schedule);
    }

    @Override
    public Page<ScheduleBaseResponse> findAllSchedule(Pageable pageable) {
        Page<Schedule> schedules = scheduleRepository.findAll(pageable);
        return scheduleMapper.toScheduleResponsePage(schedules);
    }

    @Override
    public ScheduleGroupResponse findScheduleByGroupId(Long groupId) {
        List<Schedule> scheduleList = scheduleRepository.findByGroupId(groupId);
        List<ScheduleCourseResponse> scheduleCourseResponses = scheduleList.stream()
                .map(scheduleMapper::toScheduleCourseResponse)
                .toList();

        GroupResponse groupResponse = groupMapper.toGroupResponse(getGroupById(groupId));
        return new ScheduleGroupResponse(groupResponse, scheduleCourseResponses);
    }

    @Override
    public ScheduleTeacherResponse findScheduleByTeacherId(Long teacherId) {
        Course course = courseRepository.findByTeacherId(teacherId);
        List<Schedule> scheduleList = scheduleRepository.findByCourseId(course.getId());
        List<ScheduleCourseResponse> scheduleCourseResponses = scheduleList.stream()
                .map(scheduleMapper::toScheduleCourseResponse)
                .toList();

        TeacherResponse teacherResponse = teacherMapper.toTeacherResponse(getTeacherById(teacherId));
        return new ScheduleTeacherResponse(teacherResponse, scheduleCourseResponses);
    }

    @Override
    public void updateSchedule(Long id, ScheduleRequest request) {
        Course course = getCourseById(request.getCourseId());
        Group group = getGroupById(request.getGroupId());
        Schedule schedule = getScheduleById(id);

        scheduleMapper.updateScheduleRequest(schedule, request);
        schedule.setCourse(course);
        schedule.setGroup(group);
        scheduleRepository.save(schedule);
    }

    @Override
    public void deleteById(Long id) {
        scheduleRepository.delete(getScheduleById(id));
    }

    private Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule id " + id + " is not found"));
    }

    private Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course id " + id + " is not found"));
    }

    private Group getGroupById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group id " + id + " is not found"));
    }

    private Teacher getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher id " + id + " is not found"));
    }
}
