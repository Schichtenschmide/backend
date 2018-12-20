package ch.juventus.schichtenschmiede.persistency.repository;

import ch.juventus.schichtenschmiede.persistency.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
