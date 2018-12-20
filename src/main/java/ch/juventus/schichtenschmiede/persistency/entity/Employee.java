package ch.juventus.schichtenschmiede.persistency.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "Employee")
// to resolve a lazy loading issue during JSON serialization
public class Employee extends ResourceSupport {

    @Id
    @GeneratedValue
    @Column(name = "employee_id")
    private Long stid; // avoid clash with getId from HATEOAS support

    @NotNull
    @Size(min = 2, max = 20)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 20)
    private String lastName;

    @NotNull
    private int employmentRate;

    @NotNull
    private boolean isActive;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Employee_ShiftPlan",
            joinColumns = {@JoinColumn(name = "employee_id")},
            inverseJoinColumns = {@JoinColumn(name = "shiftPlan_id")})
    private Set<ShiftPlan> shiftPlanSet = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Role role;


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

    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getStid() {
        return stid;
    }

    public void setStid(Long stid) {
        this.stid = stid;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void addShiftplan(ShiftPlan shiftPlan) {
        shiftPlanSet.add(shiftPlan);
    }

    public Set<ShiftPlan> getShiftPlanSet() {
        return shiftPlanSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(stid, employee.stid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stid);
    }
}
