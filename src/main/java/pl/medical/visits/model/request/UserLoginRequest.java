package pl.medical.visits.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class UserLoginRequest {
    private String email;
    private String password;
}
