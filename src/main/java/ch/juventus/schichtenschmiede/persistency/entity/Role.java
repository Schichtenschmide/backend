package ch.juventus.schichtenschmiede.persistency.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@Entity
@XmlRootElement
public class Role extends ResourceSupport {

    @Id
    @GeneratedValue
    private Long stid; // avoid clash with getId from HATEOAS support

    @NotNull
    @Column(unique = true)
    @Size(min = 3, max = 20)
    private String name;

    private boolean isActive;

    public Role() {
    }

    public Role(String name, boolean isActive) {
        this.name = name;
        this.isActive = isActive;

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
        if (!(o instanceof Role)) return false;
        if (!super.equals(o)) return false;
        Role role = (Role) o;
        return Objects.equals(stid, role.stid) &&
                Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), stid, name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "stid=" + stid +
                ", name='" + name + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
