package pl.medical.visits.service;

import org.springframework.stereotype.Service;
import pl.medical.visits.exception.ValidationException;
import pl.medical.visits.model.entity.user.User;
import pl.medical.visits.model.entity.user.UserAddressData;
import pl.medical.visits.model.entity.user.UserLoginData;

@Service
public interface ValidationService {
    static final String BIRTHDAY_REGEX = "^(\\d{4})(-)([0-1]\\d)(-)([0-3]\\d)$";
    static final String PHONE_NR_REGEX = "^(\\d{9,11})$";
    static final String PESEL_REGEX = "^(\\d{11})$";
    static final String POSTAL_CODE_REGEX = "^(\\d{2})(-)(\\d{3})$";
    static final String HOUSE_REGEX = "^(\\d{1,3})([a-zA-Z]?)$";
    static final String EMAIL_REGEX = "^.{2,}(@)([a-zA-Z]+)(.)([a-zA-Z]{2,})$";
    static final String MESSAGE_TEMPLATE_TOO_LONG = "ValidationException: %s is too long";
    static final String MESSAGE_TEMPLATE_NULL = "ValidationException: %s is null";
    static final String MESSAGE_TEMPLATE_REGEX = "ValidationException: %s doesn't match regex";

    public void validateUser(User user) throws ValidationException;

    void validateUserAddress(UserAddressData userAddress) throws ValidationException;

    void validateUserEmail(UserLoginData userLogin) throws ValidationException;
}
