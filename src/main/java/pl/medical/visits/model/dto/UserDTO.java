package pl.medical.visits.model.dto;

import lombok.Getter;
import lombok.ToString;
import pl.medical.visits.model.enums.Role;
import pl.medical.visits.model.entity.user.User;

@Getter
@ToString
public class UserDTO {
    private final long id;
    private final Role role;
    private final String firstName;
    private final String lastName;
    private final String pesel;
    private final String phoneNr;
    private final String email;

    public UserDTO(User user, String email) {
        this.id = user.getId();
        this.role = user.getRole();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.pesel = user.getPesel();
        this.phoneNr = user.getPhoneNr();
        this.email = email;
    }
}
