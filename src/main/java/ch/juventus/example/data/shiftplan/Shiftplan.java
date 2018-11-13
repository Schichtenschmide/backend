package ch.juventus.example.data.shiftplan;

import ch.juventus.example.data.shift.Shift;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author : ${user}
 * @since: ${date}
 */


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
// to resolve a lazy loading issue during JSON serialization
public class Shiftplan extends ResourceSupport {
    @Id
    @GeneratedValue
    private Long stid; // avoid clash with getId from HATEOAS support

    @NotNull
    private int weekNumber;

    @NotNull
    private int year;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shift_id")
    private Shift shift;

    @JsonIgnore
    private List<Shift> shifts = new ArrayList<>();

    public Shiftplan() {
    }

    public Shiftplan(int weekNumber, int year) {
        this.weekNumber = weekNumber;
        this.year = year;
    }

    public void addShift(Shift shift) {
        shifts.add(shift);
        //shift.setShiftplan(this);
    }

    public void removeShift(Shift shift) {
        shifts.remove(shift);
        //shift.setShiftplan(null);
    }

    public List<Shift> getShifts() {
        return Collections.unmodifiableList(shifts);
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
}





