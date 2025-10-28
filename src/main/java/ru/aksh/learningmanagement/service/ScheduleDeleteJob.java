package ru.aksh.learningmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.aksh.learningmanagement.domain.Schedule;
import ru.aksh.learningmanagement.repository.ScheduleRepository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduleDeleteJob {
    private final ScheduleRepository scheduleRepository;

    @Scheduled(cron = "${spring.schedule.daily}")
    public void deleteAfterOneYear() {
        int page = 0;
        Page<Schedule> oldSchedules;
        LocalDateTime createdAtYearAgo = LocalDateTime.now().minusYears(1);

        do {
            oldSchedules = scheduleRepository.findByCreatedAtBefore(createdAtYearAgo, PageRequest.of(page, 100));

            scheduleRepository.deleteAll(oldSchedules);

            page++;
        } while (oldSchedules.hasNext());
    }
}
