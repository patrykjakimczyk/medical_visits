package pl.medical.visits.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.medical.visits.model.enums.Role;

@Data
@AllArgsConstructor
public final class AuthenticationResponse {
    private String token;
    private Role role;
}
