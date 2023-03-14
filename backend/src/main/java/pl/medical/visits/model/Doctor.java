package pl.medical.visits.model;

import pl.medical.visits.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = Role.Values.DOCTOR)
public final class Doctor extends User{
    @Column(length = 50)
    private String specialty;
}
