package pl.medical.visits.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestWrapper {
    private Patient patient;
    private UserLoginData loginData;
    private UserAddressData addressData;
    private Doctor assignedDoctor;
}
