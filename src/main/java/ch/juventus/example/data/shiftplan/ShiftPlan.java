package ch.juventus.example.data.shiftplan;

import ch.juventus.example.data.shift.Shift;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author : ${user}
 * @since: ${date}
 */


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
// to resolve a lazy loading issue during JSON serialization
public class ShiftPlan extends ResourceSupport {
    @Id
    @GeneratedValue
    private Long stid; // avoid clash with getId from HATEOAS support

    @NotNull
    private int weekNumber;


    @NotNull
    private int year;


    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "shift_id")
    private Shift shift;


    public ShiftPlan() {
    }

    public ShiftPlan(int weekNumber, int year) {
        this.weekNumber = weekNumber;
        this.year = year;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShiftPlan)) return false;
        if (!super.equals(o)) return false;
        ShiftPlan shiftPlan = (ShiftPlan) o;
        return weekNumber == shiftPlan.weekNumber &&
                year == shiftPlan.year &&
                Objects.equals(stid, shiftPlan.stid) &&
                Objects.equals(shift, shiftPlan.shift);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stid, weekNumber, year, shift);
    }
}





