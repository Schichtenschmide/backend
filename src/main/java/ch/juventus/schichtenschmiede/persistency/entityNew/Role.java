package ch.juventus.schichtenschmiede.persistency.entityNew;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;
import java.util.StringJoiner;


/**
 * @author : ${user}
 * @since: ${date}
 */


@Entity
@Table(name = "Role2")
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
        return "Role{" +
                "Identifier='" + getIdentifier() + '\'' +
                ", name='" + getName() + '\'' +
                '}';
    }
}
