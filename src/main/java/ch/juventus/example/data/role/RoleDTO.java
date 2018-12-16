package ch.juventus.example.data.role;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author : ${user}
 * @since: ${date}
 */
public class RoleDTO {
    private Long stid;

    private String name;

    @JsonProperty
    private boolean isActive;

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "stid=" + stid +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
