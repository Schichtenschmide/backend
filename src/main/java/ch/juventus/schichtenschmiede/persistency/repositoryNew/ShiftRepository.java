package ch.juventus.schichtenschmiede.persistency.repositoryNew;

import ch.juventus.schichtenschmiede.persistency.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : ${user}
 * @since: ${date}
 */

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
}
