package pl.medical.visits.model.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import pl.medical.visits.model.enums.Role;

import javax.persistence.*;

@Data
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue(value = Role.Values.PATIENT)
public final class Patient extends User {
    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName= "user_id")
    private Doctor assignedDoctor;

    @OneToOne(mappedBy = "user")
    @JsonManagedReference
    private UserAddressData addressData;

    public Patient() {
        super();
        this.setRole(Role.PATIENT);
    }
}
