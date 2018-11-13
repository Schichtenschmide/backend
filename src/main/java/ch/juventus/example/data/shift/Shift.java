package ch.juventus.example.data.shift;
import ch.juventus.example.data.department.Department;
import ch.juventus.example.data.shiftplan.Shiftplan;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author : ${user}
 * @since: ${date}
 */

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
// to resolve a lazy loading issue during JSON serialization
public class Shift extends ResourceSupport {

    @Id
    @GeneratedValue
    private Long stid;

    @NotNull
    @Size(min = 2, max = 20)
    private String name;

    private int startTime;

    private int endTime;

    @NotNull
    @Size(max = 20)
    private String shorthand;


    @OneToMany(
            mappedBy = "shift",
            cascade = CascadeType.ALL
    )
/*
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shiftpla_id")
    private Shiftplan shiftplan;
    */

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Shift(){}

    public Shift(String name, int startTime, int endTime, String shorthand, Department department, int employeeCount) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.shorthand = shorthand;
        this.department = department;
        this.employeeCount = employeeCount;
    }

    /*
    public Shiftplan getShiftplan() {
        return shiftplan;
    }

    public void setShiftplan(Shiftplan shiftplan) {
        this.shiftplan = shiftplan;
    }
    */
    private int employeeCount;

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

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getShorthand() {
        return shorthand;
    }

    public void setShorthand(String shorthand) {
        this.shorthand = shorthand;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }
}
