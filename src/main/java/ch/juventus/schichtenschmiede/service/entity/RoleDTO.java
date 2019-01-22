package ch.juventus.schichtenschmiede.service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author : ${user}
 * @since: ${date}
 */
public class RoleDTO {
    private Long identifier;

    private String name;

    @JsonProperty
    private boolean isActive;

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
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
}
