package pl.medical.visits.util;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import pl.medical.visits.exception.ValidationException;
import pl.medical.visits.model.entity.user.User;
import pl.medical.visits.model.entity.user.UserAddressData;
import pl.medical.visits.model.entity.user.UserLoginData;

import java.time.LocalDate;
import java.time.Period;

@Service
@NoArgsConstructor
public class ValidationService {
    private static final String BIRTHDAY_REGEX = "^(\\d{4})(-)([0-1][1-9])(-)([0-3]\\d)$";
    private static final String PHONE_NR_REGEX = "^(\\d{9,11})$";
    private static final String PESEL_REGEX = "^(\\d{11})$";
    private static final String POSTAL_CODE_REGEX = "^(\\d{2})(-)(\\d{3})$";
    private static final String HOUSE_REGEX = "^(\\d{1,3})([a-zA-Z]?)$";
    private static final String EMAIL_REGEX = "(@)([a-zA-Z]+)(.)([a-zA-Z]{2,})$";


    public void validateUser(User user) throws ValidationException {
        if (StringUtil.isStringNotNull(user.getFirstName())) {
            if (StringUtil.isLengthOverMax(user.getFirstName(), 20)) {
                throw new ValidationException("ValidationException: First name is too long");
            }
        } else {
            throw new ValidationException("ValidationException: First name is null");
        }

        if (StringUtil.isStringNotNull(user.getLastName())) {
            if (StringUtil.isLengthOverMax(user.getLastName(), 20)) {
                throw new ValidationException("ValidationException: Last name is too long");
            }
        } else {
            throw new ValidationException("ValidationException: Last name is null");
        }

        if (StringUtil.isStringNotNull(user.getPesel())) {
            if (!user.getPesel().matches(PESEL_REGEX)) {
                throw new ValidationException("ValidationException: Pesel doesn't match regex");
            }
        } else {
            throw new ValidationException("ValidationException: Pesel is null");
        }

        if (StringUtil.isStringNotNull(user.getBirthDate())) {
            if (!user.getPesel().matches(BIRTHDAY_REGEX)) {
                throw new ValidationException("ValidationException: Birthday doesn't match regex");
            }
        } else {
            throw new ValidationException("ValidationException: Birthday is null");
        }

        if (StringUtil.isStringNotNull(user.getPhoneNr())) {
            if (!user.getPesel().matches(PHONE_NR_REGEX)) {
                throw new ValidationException("ValidationException: Phone number doesn't match regex");
            }
        } else {
            throw new ValidationException("ValidationException: Phone number is null");
        }

        if (!this.isBirthDayEqualPesel(user))
            throw new ValidationException("ValidationException: Birthday doesn't match pesel");

        if (!this.isPeselValid(user))
            throw new ValidationException("ValidationException: Birthday doesn't match pesel");
    }

    public void validateUserAddress(UserAddressData userAddress) throws ValidationException {
        if (StringUtil.isStringNotNull(userAddress.getCountry())) {
            if (StringUtil.isLengthOverMax(userAddress.getCountry(), 50)) {
                throw new ValidationException("ValidationException: Country is too long");
            }
        } else {
            throw new ValidationException("ValidationException: Country is null");
        }

        if (StringUtil.isStringNotNull(userAddress.getCity())) {
            if (StringUtil.isLengthOverMax(userAddress.getCity(), 30)) {
                throw new ValidationException("ValidationException: City is too long");
            }
        } else {
            throw new ValidationException("ValidationException: City is null");
        }

        if (StringUtil.isStringNotNull(userAddress.getStreet())) {
            if (StringUtil.isLengthOverMax(userAddress.getStreet(), 50)) {
                throw new ValidationException("ValidationException: Street is too long");
            }
        } else {
            throw new ValidationException("ValidationException: Street is null");
        }

        if (StringUtil.isStringNotNull(userAddress.getHouseNr())) {
            if (!userAddress.getHouseNr().matches(HOUSE_REGEX)) {
                throw new ValidationException("ValidationException: House nr doesn't match regex");
            }
        } else {
            throw new ValidationException("ValidationException: House nr is null");
        }

        if (StringUtil.isStringNotNull(userAddress.getApartmentNr())) {
            if (!userAddress.getApartmentNr().matches(HOUSE_REGEX)) {
                throw new ValidationException("ValidationException: Apartment nr doesn't match regex");
            }
        }

        if (StringUtil.isStringNotNull(userAddress.getPostalCode())) {
            if (!userAddress.getPostalCode().matches(POSTAL_CODE_REGEX)) {
                throw new ValidationException("ValidationException: Postal code doesn't match regex");
            }
        } else {
            throw new ValidationException("ValidationException: Postal code nr is null");
        }
    }

    public void validateUserEmail(UserLoginData userLogin) throws ValidationException {
        if (StringUtil.isStringNotNull(userLogin.getEmail())) {
            if (!userLogin.getEmail().matches(EMAIL_REGEX)) {
                throw new ValidationException("ValidationException: E-mail doesn't match regex");
            }
        } else {
            throw new ValidationException("ValidationException: E-mail code nr is null");
        }
    }

    private boolean isPeselValid(User user) {
        String pesel = user.getPesel();
        String sex = user.getSex();

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
