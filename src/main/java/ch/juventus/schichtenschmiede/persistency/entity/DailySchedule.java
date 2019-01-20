package ch.juventus.schichtenschmiede.persistency.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Calendar;
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

    private boolean isMonday;

    private boolean isTuesday;

    private boolean isWednesday;

    private boolean isThursday;

    private boolean isFriday;

    private boolean isSaturday;

    private boolean isSunday;

    @ManyToMany
    @JoinTable(name = "daily_schedule_employees",
            joinColumns = { @JoinColumn(name = "fk_dailyschedule") },
            inverseJoinColumns = { @JoinColumn(name = "fk_employee") })
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
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        this.isMonday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
        this.isTuesday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY;
        this.isWednesday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY;
        this.isThursday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY;
        this.isFriday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
        this.isSaturday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
        this.isSunday = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isMonday() {
        return isMonday;
    }

    public void setMonday(boolean monday) {
        isMonday = monday;
    }

    public boolean isTuesday() {
        return isTuesday;
    }

    public void setTuesday(boolean tuesday) {
        isTuesday = tuesday;
    }

    public boolean isWednesday() {
        return isWednesday;
    }

    public void setWednesday(boolean wednesday) {
        isWednesday = wednesday;
    }

    public boolean isThursday() {
        return isThursday;
    }

    public void setThursday(boolean thursday) {
        isThursday = thursday;
    }

    public boolean isFriday() {
        return isFriday;
    }

    public void setFriday(boolean friday) {
        isFriday = friday;
    }

    public boolean isSaturday() {
        return isSaturday;
    }

    public void setSaturday(boolean saturday) {
        isSaturday = saturday;
    }

    public boolean isSunday() {
        return isSunday;
    }

    public void setSunday(boolean sunday) {
        isSunday = sunday;
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
