package ch.juventus.schichtenschmiede.persistency.entityNew;

import ch.juventus.schichtenschmiede.persistency.entity.Employee;
import ch.juventus.schichtenschmiede.persistency.entity.Shift;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "ShiftPlan", uniqueConstraints = @UniqueConstraint(columnNames = {"weekNumber", "year"}))
// to resolve a lazy loading issue during JSON serialization
public class ShiftPlan extends BaseEntity {

    @NotNull
    private int weekNumber;

    @NotNull
    private int year;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<DailySchedule> dailySchedules;


    public ShiftPlan() {
        super();
    }

    public ShiftPlan(boolean isActive, int weekNumber, int year) {
        super(isActive);
        this.weekNumber = weekNumber;
        this.year = year;
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

    public Set<DailySchedule> getDailySchedules() {
        return dailySchedules;
    }

    public void setDailySchedules(Set<DailySchedule> dailySchedules) {
        this.dailySchedules = dailySchedules;
    }

    public void addDailySchedule(DailySchedule dailySchedule) {
        this.dailySchedules.add(dailySchedule);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShiftPlan)) return false;

        ShiftPlan shiftPlan = (ShiftPlan) o;
        return Objects.equals(getIdentifier(), shiftPlan.getIdentifier())
                && getWeekNumber() == shiftPlan.getWeekNumber()
                && getYear() == shiftPlan.getYear()
                && Objects.equals(getDailySchedules(), shiftPlan.getDailySchedules());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier(), getWeekNumber(), getYear(), getDailySchedules());
    }

    @Override
    public String toString() {
        return "ShiftPlan{" +
                "identifier='" + getIdentifier() + '\'' +
                ", weekNumber=" + weekNumber +
                ", year=" + year +
                ", dailySchedules=" + dailySchedules +
                '}';
    }
}