package pl.medical.visits.model;

import pl.medical.visits.model.enums.Role;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@DiscriminatorValue(value = Role.Values.PATIENT)
public final class Patient extends User{
}
