package pl.medical.visits.service;

import org.springframework.stereotype.Service;
import pl.medical.visits.model.entity.user.User;
import pl.medical.visits.model.entity.user.UserAddressData;
import pl.medical.visits.model.entity.user.UserLoginData;

import java.sql.Timestamp;

@Service
public interface ValidationService {
    String BIRTHDAY_REGEX = "^(\\d{4})(-)([0-1]\\d)(-)([0-3]\\d)$";
    String PHONE_NR_REGEX = "^(\\d{9,11})$";
    String PESEL_REGEX = "^(\\d{11})$";
    String POSTAL_CODE_REGEX = "^(\\d{2})(-)(\\d{3})$";
    String HOUSE_REGEX = "^(\\d{1,3})([a-zA-Z]?)$";
    String EMAIL_REGEX = "^.{2,}(@)([a-zA-Z]+)(.)([a-zA-Z]{2,})$";
    String MESSAGE_TEMPLATE_TOO_LONG = "ValidationException: %s is too long";
    String MESSAGE_TEMPLATE_NULL = "ValidationException: %s is null";
    String MESSAGE_TEMPLATE_REGEX = "ValidationException: %s doesn't match regex";

    boolean isTimestampCorrect(Timestamp timestamp);
    void validateUser(User user);
    void validateUserAddress(UserAddressData userAddress);
    void validateUserEmail(UserLoginData userLogin);
}
