package ch.juventus.schichtenschmiede.persistency.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author : ${user}
 * @since: ${date}
 */

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "Employee")
// to resolve a lazy loading issue during JSON serialization
public class Employee extends BaseEntity {

    @NotNull
    @Size(min = 2, max = 20)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 20)
    private String lastName;

    @NotNull
    @Min(20)
    @Max(100)
    private int employmentRate;


    @ManyToOne
    @NotNull
    @JoinColumn(name = "role_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Role role;

    public Employee() {
        super();
    }

    public Employee(boolean isActive, String firstName, String lastName, int employmentRate, Role role) {
        super(isActive);
        this.firstName = firstName;
        this.lastName = lastName;
        this.employmentRate = employmentRate;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getEmploymentRate() {
        return employmentRate;
    }

    public void setEmploymentRate(int employmentRate) {
        this.employmentRate = employmentRate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;
        return Objects.equals(getIdentifier(), employee.getIdentifier())
                && Objects.equals(getFirstName(), employee.getFirstName())
                && Objects.equals(getLastName(), employee.getLastName())
                && getEmploymentRate() == employee.getEmploymentRate();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier(), getFirstName(), getLastName(), getEmploymentRate());
    }

    @Override
    public String toString() {
        return "EmployeeOld{" +
                "identifier='" + getIdentifier() + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", employmentRate=" + employmentRate +
                ", role=" + role +
                '}';
    }
}
