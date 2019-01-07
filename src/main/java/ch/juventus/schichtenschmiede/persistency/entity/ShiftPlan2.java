package ch.juventus.schichtenschmiede.persistency.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author : ${user}
 * @since: ${date}
 */

//TODO Dupluzierung für die Anpassung des neuen Schichtenplans Alex und Miguel. Wir bei fertigstellung in Shiftplan übernommen.
//TODO Check DB verknüpfungen, Datum Woche Jahr, Schichtenauswahl

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "ShiftPlan2", uniqueConstraints = @UniqueConstraint(columnNames = {"weekNumber", "year"}))
// to resolve a lazy loading issue during JSON serialization
public class ShiftPlan2 extends ResourceSupport {
    @Id
    @GeneratedValue
    @Column(name = "shiftPlan_id")
    private Long stid; // avoid clash with getId from HATEOAS support

    @NotNull
    private int weekNumber;

    @NotNull
    private int year;

    @NotNull
    private boolean isActive;

    @ManyToMany(mappedBy = "shiftPlanSet")
    private Set<Employee> employees = new HashSet<>();

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "shift_id")
    private Shift shift;

    public ShiftPlan2() {
    }

    public ShiftPlan2(int weekNumber, int year, boolean isActive) {
        this.weekNumber = weekNumber;
        this.year = year;
        this.isActive = isActive;
    }

    public Long getStid() {
        return stid;
    }

    public void setStid(Long stid) {
        this.stid = stid;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShiftPlan2)) return false;
        if (!super.equals(o)) return false;
        ShiftPlan2 shiftPlan = (ShiftPlan2) o;
        return Objects.equals(stid, shiftPlan.stid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stid);
    }


}





