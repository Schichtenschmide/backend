package ch.juventus.schichtenschmiede.persistency.repository;

import ch.juventus.schichtenschmiede.persistency.entity.ShiftPlan2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : ${user}
 * @since: ${date}
 */
//TODO Dupluzierung für die Anpassung des neuen Schichtenplans Alex und Miguel. Wir bei fertigstellung in Shiftplan übernommen.

@Repository
public interface ShiftPlan2Repository extends JpaRepository<ShiftPlan2, Long> {
}
