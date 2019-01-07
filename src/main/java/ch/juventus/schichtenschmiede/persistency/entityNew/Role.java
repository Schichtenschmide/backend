package ch.juventus.schichtenschmiede.persistency.entityNew;

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
public class Role extends BaseEntity {

    @NotNull
    @Column(unique = true)
    @Size(min = 3, max = 20)
    private String name;


    public Role(String name, boolean isActive) {
        super(false);
        this.name = name;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        //TODO prüfen ob notwendig
        //if (!super.equals(o)) return false;
        Role role = (Role) o;
        return Objects.equals(super.getIdentifier(), role.getIdentifier()) &&
                Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        //TODO prüfen wie Linda
        return 0;
    }

    @Override
    public String toString() {
        return "Role{" +
                "identifier=" + super.getIdentifier() +
                ", name='" + name + '\'' +
                ", isActive=" + super.isActive() +
                '}';
    }
}
