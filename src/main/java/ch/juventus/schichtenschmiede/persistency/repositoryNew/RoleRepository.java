package ch.juventus.schichtenschmiede.persistency.repositoryNew;

import ch.juventus.schichtenschmiede.persistency.entityNew.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
