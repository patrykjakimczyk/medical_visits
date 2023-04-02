package pl.medical.visits.dto;

import lombok.Getter;
import lombok.ToString;
import pl.medical.visits.model.enums.Role;
import pl.medical.visits.model.user.User;

@Getter
@ToString
public class UserDTO {
    private final long id;
    private final Role role;
    private final String firstName;
    private final String lastName;
    private final String pesel;
    private final String birthDate;
    private final String sex;
    private final String phoneNr;

    public UserDTO(User user) {
        this.id = user.getId();
        this.role = user.getRole();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.pesel = user.getPesel();
        this.birthDate = user.getBirthDate();
        this.sex = user.getSex();
        this.phoneNr = user.getPhoneNr();
    }
}
