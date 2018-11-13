package ch.juventus.example.data.employee;

import ch.juventus.example.data.department.Department;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // to resolve a lazy loading issue during JSON serialization
public class Employee extends ResourceSupport {

    @Id
    @GeneratedValue
    private Long stid; // avoid clash with getId from HATEOAS support

    @NotNull
    @Size(min = 2, max = 20)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 20)
    private String lastName;

    private int employmentRate;

    private boolean isActive;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


    public Employee() { // why JPA why??
    }

    public Employee(String firstName, String lastName, int employmentRate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employmentRate = employmentRate;
        this.isActive = true;
    }

    public int getEmploymentRate() {
        return employmentRate;
    }

    public void setEmploymentRate(int employmentRate) {
        this.employmentRate = employmentRate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {         isActive = active;
    }

    public void setStid(Long stid) {
        this.stid = stid;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getStid() {
        return stid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        return stid != null ? stid.equals(employee.stid) : employee.stid == null;
    }

    @Override
    public int hashCode() {
        return stid != null ? stid.hashCode() : 0;
    }
}