package ch.juventus.schichtenschmiede.persistency.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;


/**
 * @author : ${user}
 * @since: ${date}
 */


@Entity
@Table(name = "Role")
public class Role extends BaseEntity {

    @NotNull
    @Column(unique = true)
    @Size(min = 3, max = 20)
    private String name;


    public Role() {
        super();
    }

    public Role(boolean isActive, String name) {
        super(isActive);
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

        Role role = (Role) o;
        return Objects.equals(getIdentifier(), role.getIdentifier())
                && Objects.equals(getName(), role.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier(), getName());
    }

    @Override
    public String toString() {
        return "RoleOld{" +
                "Identifier='" + getIdentifier() + '\'' +
                ", name='" + getName() + '\'' +
                '}';
    }
}
