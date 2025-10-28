package ru.aksh.learningmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.aksh.learningmanagement.domain.Schedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Page<Schedule> findByGroupId(Long groupId, Pageable pageable);

    Page<Schedule> findByCourseId(Long courseId, Pageable pageable);

    Page<Schedule> findByCreatedAtBefore(LocalDateTime dateTime, Pageable pageable);
}
