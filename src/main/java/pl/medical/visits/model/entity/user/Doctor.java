package pl.medical.visits.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import pl.medical.visits.model.enums.Role;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@SuperBuilder
@Entity
@DiscriminatorValue(value = Role.Values.DOCTOR)
public final class Doctor extends User {
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "doctor_speciality",
            joinColumns = @JoinColumn(
                    name = "doctor_id",
                    referencedColumnName = "user_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "speciality_id",
                    referencedColumnName = "speciality_id"
            )
    )
    private List<Speciality> specialities;

    public Doctor() {
        super();
        this.setRole(Role.DOCTOR);
    }
}
