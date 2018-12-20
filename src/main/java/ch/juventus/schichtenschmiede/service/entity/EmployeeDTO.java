package ch.juventus.schichtenschmiede.service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author : ${user}
 * @since: ${date}
 */
public class EmployeeDTO {


    private Long stid;

    private String firstName;

    private String lastName;

    private int employmentRate;

    @JsonProperty
    private boolean isActive;

    private Long shiftPlanId;

    private Long roleId;

    public Long getStid() {
        return stid;
    }

    public void setStid(Long stid) {
        this.stid = stid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getEmploymentRate() {
        return employmentRate;
    }

    public void setEmploymentRate(int employmentRate) {
        this.employmentRate = employmentRate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getShiftPlanId() {
        return shiftPlanId;
    }

    public void setShiftPlanId(Long shiftPlanId) {
        this.shiftPlanId = shiftPlanId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
