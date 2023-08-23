package pl.medical.visits.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.medical.visits.config.JwtService;
import pl.medical.visits.exception.NotUniqueValueException;
import pl.medical.visits.exception.UserDoesNotExistException;
import pl.medical.visits.model.entity.user.*;
import pl.medical.visits.model.request.DoctorRegistrationRequest;
import pl.medical.visits.model.request.PatientRegistrationRequest;
import pl.medical.visits.model.request.UserLoginRequest;
import pl.medical.visits.model.response.AuthenticationResponse;
import pl.medical.visits.repository.UserAddressRepository;
import pl.medical.visits.repository.UserLoginRepository;
import pl.medical.visits.service.RegistrationService;
import pl.medical.visits.service.ValidationService;
import pl.medical.visits.util.StringUtil;

@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final ValidationService validationService;
    private final UserLoginRepository userLoginRepository;
    private final UserAddressRepository userAddressRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthenticationResponse registerPatient(PatientRegistrationRequest request) {
        Patient givenPatient = request.getPatient();
        UserLoginData givenLoginData = request.getLoginData();
        validateUserAndEmail(givenPatient, request.getLoginData());

        UserAddressData addressData = request.getAddressData();
        validationService.validateUserAddress(addressData);
        addressData.setUser(givenPatient);

        try {
            userLoginRepository.save(givenLoginData);
            userAddressRepository.save(addressData);
        } catch (RuntimeException e) {
            throw new NotUniqueValueException(
                    "Error has occurred during user's registration. PESEL/e-mail/phone number isn't unique"
            );
        }
        UserLoginRequest userLoginRequest = new UserLoginRequest(givenLoginData.getEmail(), givenLoginData.getPassword());
        return loginUser(userLoginRequest);
    }

    public AuthenticationResponse registerDoctor(DoctorRegistrationRequest request) {
        Doctor givenDoctor = request.getDoctor();
        UserLoginData givenLoginData = request.getLoginData();
        validateUserAndEmail(givenDoctor, givenLoginData);

        try {
            userLoginRepository.save(givenLoginData);
        } catch (RuntimeException e) {
            throw new NotUniqueValueException(
                    "Error has occurred during user's registration. PESEL/e-mail/phone number isn't unique"
            );
        }
        UserLoginRequest userLoginRequestWrapper = new UserLoginRequest(givenLoginData.getEmail(), givenLoginData.getPassword());
        return loginUser(userLoginRequestWrapper);
    }

    private void validateUserAndEmail(User user, UserLoginData loginData) {
        validationService.validateUser(user);

        user.setFirstName(StringUtil.firstCapital(user.getFirstName()));
        user.setLastName(StringUtil.firstCapital(user.getLastName()));

        validationService.validateUserEmail(loginData);
        loginData.setPassword(passwordEncoder.encode(loginData.getPassword()));
        loginData.setUser(user);
    }

    public AuthenticationResponse loginUser(UserLoginRequest userLogin) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLogin.getEmail(),
                        userLogin.getPassword()
                )
        );
        UserLoginData userLoginData = userLoginRepository.findByEmail(userLogin.getEmail());
        User user = userLoginData.getUser();
        String token = jwtService.generateToken(userLoginData);

        if (user != null) return new AuthenticationResponse(token, user.getRole());
        throw new UserDoesNotExistException("User does not exist in database");
    }
}
