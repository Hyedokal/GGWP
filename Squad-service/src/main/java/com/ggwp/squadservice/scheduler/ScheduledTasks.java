package com.ggwp.squadservice.scheduler;

import com.ggwp.squadservice.domain.Squad;
import com.ggwp.squadservice.repository.SquadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

//outdated 필드를 매일 자정마다 지속적으로 업데이트하는 스케줄러.
@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private final SquadRepository squadRepository;

    //매일 자정에 실행
    @Scheduled(cron = "0 0 0 * * *")
    public void updateOutdatedPosts() {
        Timestamp oneWeekAgo = Timestamp.valueOf(LocalDateTime.now().minusDays(7));    //7일 전
        List<Squad> outdatedSquads = squadRepository.findByCreatedAtBeforeAndOutdatedFalse(oneWeekAgo);
        outdatedSquads.forEach(squad -> {
            squad.setOutdated(true);
            squadRepository.save(squad);
        });
    }
}
