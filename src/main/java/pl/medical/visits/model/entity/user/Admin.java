package pl.medical.visits.model.entity.user;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import pl.medical.visits.model.enums.Role;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@SuperBuilder
@DiscriminatorValue(value = Role.Values.ADMIN)
public final class Admin extends User{
    public Admin() {
        super();
        this.setRole(Role.ADMIN);
    }
}
