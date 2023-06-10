package pl.medical.visits.model.wrapper;

import lombok.Data;

@Data
public class PatientEditDataForPatientWrapper {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String phoneNr;
    private final String email;
    private final String country;
    private final String city;
    private final String street;
    private final String houseNr;
    private final String apartmentNr;
    private final String postalCode;
}
