package ch.juventus.example.data.role;

import ch.juventus.example.data.employee.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@XmlRootElement
public class Role extends ResourceSupport {

    @Id
    @GeneratedValue
    private Long stid; // avoid clash with getId from HATEOAS support

    @NotNull
    @Column(unique = true)
    @Size(min = 3, max = 20)
    private String name;

    @JsonProperty
    private boolean isActive;

    @OneToMany(
            mappedBy = "role",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Employee> employees = new ArrayList<>();

    public Role() {
    }

    public Role(String name) {
        this.name = name;
        this.isActive = true;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setRole(this);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setRole(null);
    }

    public Long getStid() {
        return stid;
    }

    public void setStid(Long stid) {
        this.stid = stid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        if (!super.equals(o)) return false;
        Role role = (Role) o;
        return isActive == role.isActive &&
                Objects.equals(stid, role.stid) &&
                Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stid, name, isActive);
    }
}
