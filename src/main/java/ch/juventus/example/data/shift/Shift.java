package ch.juventus.example.data.shift;

import ch.juventus.example.data.role.Role;
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
public class Shift extends ResourceSupport {

    @Id
    @GeneratedValue
    private Long stid;

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

    @NotNull
    @Size(max = 20)
    private String shorthand;

    private int employeeCount;

    private boolean isActive;

    @OneToMany(
            mappedBy = "shift",
            cascade = CascadeType.ALL
    )

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id")
    private Role role;

    public Shift() {
    }

    public Shift(String name, int startTime, int endTime, boolean isMonday, boolean isTuesday, boolean isWednesday, boolean isThursday, boolean isFriday, boolean isSaturday, boolean isSunday, String shorthand, boolean isActive, int employeeCount) {
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
        this.shorthand = shorthand;
        this.isActive = isActive;
        this.employeeCount = employeeCount;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
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
        if (!(o instanceof Shift)) return false;
        if (!super.equals(o)) return false;
        Shift shift = (Shift) o;
        return startTime == shift.startTime &&
                endTime == shift.endTime &&
                employeeCount == shift.employeeCount &&
                Objects.equals(stid, shift.stid) &&
                Objects.equals(name, shift.name) &&
                Objects.equals(shorthand, shift.shorthand) &&
                Objects.equals(role, shift.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stid, name, startTime, endTime, shorthand, employeeCount, role);
    }
}
