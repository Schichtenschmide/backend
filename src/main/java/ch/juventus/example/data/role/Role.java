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
    /*
    @OneToMany(
            mappedBy = "role",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Employee> employees = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
*/

    public Role() {
    }

    public Role(String name) {
        this.name = name;
        this.isActive = true;
    }

    /*
    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setRole(this);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setRole(null);
    }
    */
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


    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Role{" +
                "stid=" + stid +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
