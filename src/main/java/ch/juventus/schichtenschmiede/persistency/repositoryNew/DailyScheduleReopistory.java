package ch.juventus.schichtenschmiede.persistency.repositoryNew;

import ch.juventus.schichtenschmiede.persistency.entityNew.DailySchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: Alexandra
 * @since: 19.01.2019
 **/
@Repository
public interface DailyScheduleReopistory extends JpaRepository<DailySchedule, Long> {
}

