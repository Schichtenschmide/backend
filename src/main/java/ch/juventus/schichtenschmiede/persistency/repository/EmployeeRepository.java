package ch.juventus.schichtenschmiede.persistency.repository;

import ch.juventus.schichtenschmiede.persistency.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
