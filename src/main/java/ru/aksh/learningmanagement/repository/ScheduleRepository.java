package ru.aksh.learningmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aksh.learningmanagement.domain.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByGroupId(Long groupId);

    List<Schedule> findByCourseId(Long courseId);

    List<Schedule> findByCreationDateBefore(LocalDateTime dateTime);
}
