package pl.medical.visits.model.request;

import lombok.Data;
import pl.medical.visits.model.enums.Role;

@Data
public final class PatientEditDataForAdminWrapper {
    private final long id;
    private final Role role;
    private final String firstName;
    private final String lastName;
    private final String pesel;
    private final String phoneNr;
    private final String email;
    private final long assignedDoctorId;
    private final String country;
    private final String city;
    private final String street;
    private final String houseNr;
    private final String apartmentNr;
    private final String postalCode;
}
