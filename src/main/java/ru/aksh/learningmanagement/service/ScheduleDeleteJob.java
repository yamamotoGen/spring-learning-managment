package ru.aksh.learningmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.aksh.learningmanagement.domain.Schedule;
import ru.aksh.learningmanagement.repository.ScheduleRepository;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleDeleteJob {
    private final ScheduleRepository scheduleRepository;

    @Scheduled(cron = "${spring.schedule.daily}")
    public void deleteAfterOneYear() {
        int page = 0;
        long totalDeleteRecords = 0L;
        Page<Schedule> oldSchedules;
        LocalDateTime createdAtYearAgo = LocalDateTime.now().minusYears(1);

        log.info("Удаление расписаний schedules, созданных ранее: {}", createdAtYearAgo);

        do {
            oldSchedules = scheduleRepository.findByCreatedAtBefore(createdAtYearAgo, PageRequest.of(page, 100));
            long amountRecords = oldSchedules.getContent().size();

            if (amountRecords > 0) {
                scheduleRepository.deleteAll(oldSchedules);
                totalDeleteRecords += amountRecords;

                log.debug("Удалено {} расписаний schedules на {} странице", amountRecords, page);
            }
            page++;

            if (totalDeleteRecords > 0) {
                log.info("Удалено {} расписаний schedules старше одного года", totalDeleteRecords);
            } else {
                log.info("Отсутствуют расписания schedules старше одного года");
            }
        } while (oldSchedules.hasNext());
    }
}
