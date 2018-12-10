package ch.juventus.example.data.shiftplan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : ${user}
 * @since: ${date}
 */

@Repository
public interface ShiftPlanRepository extends JpaRepository<ShiftPlan, Long> {
}
