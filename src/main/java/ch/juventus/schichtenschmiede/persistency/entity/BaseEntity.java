package ch.juventus.schichtenschmiede.persistency.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author : ${user}
 * @since: ${date}
 */


@MappedSuperclass
public abstract class BaseEntity extends ResourceSupport {

    @Id
    @GeneratedValue
    private Long identifier;

    @JsonProperty
    private boolean isActive;


    public BaseEntity() {
    }

    public BaseEntity(boolean isActive) {
        this.isActive = isActive;
    }


    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    @JsonProperty("isActive")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    //abstract methods
    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();

}
