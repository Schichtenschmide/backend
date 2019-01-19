package ch.juventus.schichtenschmiede.persistency.entityNew;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * @author : ${user}
 * @since: ${date}
 */

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
// to resolve a lazy loading issue during JSON serialization
@Table(name = "Shift2")
public class Shift extends BaseEntity {

    @NotNull
    @Column(unique = true)
    @Size(min = 3, max = 20)
    private String name;

    private int startTime;

    private int endTime;

    private boolean isMonday;

    private boolean isTuesday;

    private boolean isWednesday;

    private boolean isThursday;

    private boolean isFriday;

    private boolean isSaturday;

    private boolean isSunday;

    private int employeeCount;


    @ManyToOne(cascade = CascadeType.MERGE)
    @NotNull
    @JoinColumn(name = "role_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Role role;

    public Shift() {
        super();
    }

    public Shift(boolean isActive, String name, int startTime, int endTime,
                 boolean isMonday, boolean isTuesday, boolean isWednesday,
                 boolean isThursday, boolean isFriday, boolean isSaturday,
                 boolean isSunday, int employeeCount, Role role) {

        super(isActive);
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isMonday = isMonday;
        this.isTuesday = isTuesday;
        this.isWednesday = isWednesday;
        this.isThursday = isThursday;
        this.isFriday = isFriday;
        this.isSaturday = isSaturday;
        this.isSunday = isSunday;
        this.employeeCount = employeeCount;
        this.role = role;
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
    @JsonProperty("isMonday")
    public boolean isMonday() {
        return isMonday;
    }

    public void setMonday(boolean monday) {
        isMonday = monday;
    }
    @JsonProperty("isTuesday")
    public boolean isTuesday() {
        return isTuesday;
    }

    public void setTuesday(boolean tuesday) {
        isTuesday = tuesday;
    }
    @JsonProperty("isWednesday")
    public boolean isWednesday() {
        return isWednesday;
    }

    public void setWednesday(boolean wednesday) {
        isWednesday = wednesday;
    }
    @JsonProperty("isThursday")
    public boolean isThursday() {
        return isThursday;
    }

    public void setThursday(boolean thursday) {
        isThursday = thursday;
    }
    @JsonProperty("isFriday")
    public boolean isFriday() {
        return isFriday;
    }

    public void setFriday(boolean friday) {
        isFriday = friday;
    }
    @JsonProperty("isSaturday")
    public boolean isSaturday() {
        return isSaturday;
    }

    public void setSaturday(boolean saturday) {
        isSaturday = saturday;
    }
    @JsonProperty("isSunday")
    public boolean isSunday() {
        return isSunday;
    }

    public void setSunday(boolean sunday) {
        isSunday = sunday;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
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
        if (!(o instanceof Shift)) return false;

        Shift shift = (Shift) o;
        return Objects.equals(getIdentifier(), shift.getIdentifier())
                && Objects.equals(getName(), shift.getName())
                && getStartTime() == shift.getStartTime()
                && getEndTime() == shift.getEndTime()
                && getEmployeeCount() == shift.getEmployeeCount()
                && Objects.equals(getRole(), shift.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier(), getName(), getStartTime(), getEndTime(), getEmployeeCount(), getRole());
    }

    @Override
    public String toString() {
        return "ShiftOld{" +
                "identifier='" + getIdentifier() + '\'' +
                ", name='" + name + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", isMonday=" + isMonday +
                ", isTuesday=" + isTuesday +
                ", isWednesday=" + isWednesday +
                ", isThursday=" + isThursday +
                ", isFriday=" + isFriday +
                ", isSaturday=" + isSaturday +
                ", isSunday=" + isSunday +
                ", employeeCount=" + employeeCount +
                ", role=" + role +
                '}';
    }
}
