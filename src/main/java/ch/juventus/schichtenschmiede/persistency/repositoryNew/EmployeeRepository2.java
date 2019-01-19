package ch.juventus.schichtenschmiede.persistency.repositoryNew;

import ch.juventus.schichtenschmiede.persistency.entityNew.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//TODO Miguel
@Repository
public interface EmployeeRepository2 extends JpaRepository<Employee, Long> {
}
