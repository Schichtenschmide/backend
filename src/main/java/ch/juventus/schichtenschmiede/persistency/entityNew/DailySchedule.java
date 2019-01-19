package ch.juventus.schichtenschmiede.persistency.entityNew;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author : ${user}
 * @since: ${date}
 */

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "DailySchedule")
public class DailySchedule extends BaseEntity {

    @NotNull
    private Date date;

    @ManyToMany
    private Set<Employee> employees = new HashSet<>();;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "shift_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Shift shift;


    public DailySchedule() {
        super();
    }

    public DailySchedule(boolean isActive, Date date, Shift shift) {
        super(isActive);
        this.date = date;
        this.shift = shift;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        this.employees.remove(employee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DailySchedule)) return false;

        DailySchedule dailySchedule = (DailySchedule) o;
        return Objects.equals(getIdentifier(), dailySchedule.getIdentifier())
                && Objects.equals(getDate(), dailySchedule.getDate())
                && Objects.equals(getEmployees(), dailySchedule.getEmployees())
                && Objects.equals(getShift(), dailySchedule.getShift());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier(), getDate(), getEmployees(), getShift());
    }

    @Override
    public String toString() {
        return "DailySchedule{" +
                "date=" + date +
                ", employees=" + employees +
                ", shift=" + shift +
                '}';
    }
}
