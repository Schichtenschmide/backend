package ch.juventus.schichtenschmiede.service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author : ${user}
 * @since: ${date}
 */
//TODO Dupluzierung für die Anpassung des neuen Schichtenplans Alex und Miguel. Wir bei fertigstellung in Shiftplan übernommen.


public class ShiftPlan2DTO {

    private Long stid;

    private int weekNumber;

    private int year;

    @JsonProperty
    private boolean isActive;

    private Long employeeId;

    private Long shiftId;

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }
}
