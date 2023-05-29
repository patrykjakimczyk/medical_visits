package pl.medical.visits.model.dto;

import lombok.Getter;
import lombok.ToString;
import pl.medical.visits.model.entity.user.Patient;

@Getter
@ToString
public class PatientDetailsDTO extends PatientDTO{
    private final UserAddressDTO userAddress;

    public PatientDetailsDTO(Patient patient, UserAddressDTO userAddress, String email) {
        super(patient, email);
        this.userAddress = userAddress;
    }
}
