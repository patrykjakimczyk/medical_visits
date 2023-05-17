package pl.medical.visits.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import pl.medical.visits.exception.ValidationException;
import pl.medical.visits.model.entity.user.User;
import pl.medical.visits.model.entity.user.UserAddressData;
import pl.medical.visits.model.entity.user.UserLoginData;
import pl.medical.visits.util.StringUtil;

import java.time.LocalDate;
import java.time.Period;

@Service
@NoArgsConstructor
public class ValidationService {
    private static final String BIRTHDAY_REGEX = "^(\\d{4})(-)([0-1]\\d)(-)([0-3]\\d)$";
    private static final String PHONE_NR_REGEX = "^(\\d{9,11})$";
    private static final String PESEL_REGEX = "^(\\d{11})$";
    private static final String POSTAL_CODE_REGEX = "^(\\d{2})(-)(\\d{3})$";
    private static final String HOUSE_REGEX = "^(\\d{1,3})([a-zA-Z]?)$";
    private static final String EMAIL_REGEX = "^.{2,}(@)([a-zA-Z]+)(.)([a-zA-Z]{2,})$";
    private static final String MESSAGE_TEMPLATE_TOO_LONG = "ValidationException: %s is too long";
    private static final String MESSAGE_TEMPLATE_NULL = "ValidationException: %s is null";
    private static final String MESSAGE_TEMPLATE_REGEX = "ValidationException: %s doesn't match regex";

    public void validateUser(User user) throws ValidationException {
        this.checkStringWithLength(user.getFirstName(), 20, "First name");
        this.checkStringWithLength(user.getLastName(), 30, "Last name");
        this.checkStringWithRegex(user.getPesel(), PESEL_REGEX, "Pesel");
        this.checkStringWithRegex(user.getBirthDate(), BIRTHDAY_REGEX, "Birthday");
        this.checkStringWithRegex(user.getPhoneNr(), PHONE_NR_REGEX, "Phone nr");

        if (!this.isBirthDayEqualPesel(user))
            throw new ValidationException("ValidationException: Birthday doesn't match pesel or user is under 18");

        if (!this.isPeselValid(user))
            throw new ValidationException("ValidationException: Pesel is invalid");
    }

    public void validateUserAddress(UserAddressData userAddress) throws ValidationException {
        this.checkStringWithLength(userAddress.getCountry(), 50, "Country");
        this.checkStringWithLength(userAddress.getCity(), 30, "City");
        this.checkStringWithLength(userAddress.getStreet(), 50, "Street");
        this.checkStringWithRegex(userAddress.getHouseNr(), HOUSE_REGEX, "House nr");

        if (StringUtil.isStringNotNull(userAddress.getApartmentNr()) &&
                !userAddress.getApartmentNr().matches(HOUSE_REGEX)
        ) {
            throw new ValidationException("ValidationException: Apartment nr doesn't match regex");
        }

        this.checkStringWithRegex(userAddress.getPostalCode(), POSTAL_CODE_REGEX, "Postal code nr");
    }

    public void validateUserEmail(UserLoginData userLogin) throws ValidationException {
        checkStringWithRegex(userLogin.getEmail(), EMAIL_REGEX, "E-mail");
    }

    private void checkStringWithLength(String string, int length, String value) throws ValidationException {
        if (StringUtil.isStringNotNull(string)) {
            if (StringUtil.isLengthOverMax(string, length)) {
                throw new ValidationException(String.format(MESSAGE_TEMPLATE_TOO_LONG, value));
            }
        } else {
            throw new ValidationException(String.format(MESSAGE_TEMPLATE_NULL, value));
        }
    }

    private void checkStringWithRegex(String string, String regex, String value) throws ValidationException {
        if (StringUtil.isStringNotNull(string)) {
            if (!string.matches(regex)) {
                throw new ValidationException(String.format(MESSAGE_TEMPLATE_REGEX, value));
            }
        } else {
            throw new ValidationException(String.format(MESSAGE_TEMPLATE_NULL, value));
        }
    }

    private boolean isPeselValid(User user) {
        String pesel = user.getPesel();
        String sex = user.getGender();

        if (pesel.length() != 11) return false;

        char[] pArray = pesel.toCharArray();

        if (!isBirthDayEqualPesel(user)) return false;
        if (!isPeselSumValid(pArray)) return false;
        if(pArray[10] % 2 == 0 && sex.equals("Female")) return true;
        return pArray[10] % 2 != 0 && sex.equals("Male");
    }

    private boolean isPeselSumValid(char[] pArray) {
        int sum = Character.getNumericValue(pArray[0]) +
                Character.getNumericValue(pArray[1]) * 3 +
                Character.getNumericValue(pArray[2]) * 7 +
                Character.getNumericValue(pArray[3]) * 9 +
                Character.getNumericValue(pArray[4]) +
                Character.getNumericValue(pArray[5]) * 3 +
                Character.getNumericValue(pArray[6]) * 7 +
                Character.getNumericValue(pArray[7]) * 9 +
                Character.getNumericValue(pArray[8]) +
                Character.getNumericValue(pArray[9]) * 3;

        sum %= 10;
        sum = 10 - sum;
        sum %= 10;

        return sum == Character.getNumericValue(pArray[10]);
    }

    private boolean isBirthDayEqualPesel(User user) {
        String birthDate[] = user.getBirthDate().split("-");
        String pesel = user.getPesel();
        String peselYear = pesel.substring(0, 2);
        int peselMonth = Integer.parseInt(pesel.substring(2, 4));
        String peselDay = pesel.substring(4, 6);
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDay = LocalDate.of(
                Integer.parseInt(birthDate[0]),
                Integer.parseInt(birthDate[1]),
                Integer.parseInt(birthDate[2])
        );

        if (Period.between(birthDay, currentDate).getYears() < 18) return false;

        if (peselMonth >= 21 && peselMonth < 33) {
            peselMonth -= 20;
        } else if (peselMonth >= 41 && peselMonth < 53) {
            peselMonth -= 40;
        } else if (peselMonth >= 61 && peselMonth < 73) {
            peselMonth -= 60;
        } else if (peselMonth >= 81 && peselMonth < 93) {
            peselMonth -= 80;
        } else if(peselMonth > 12 && peselMonth < 21) return false;

        if (!birthDate[2].equals(peselDay)) return false;
        if (Integer.parseInt(birthDate[1]) != peselMonth) return false;
        return birthDate[0].substring(2).equals(peselYear);
    }
}
