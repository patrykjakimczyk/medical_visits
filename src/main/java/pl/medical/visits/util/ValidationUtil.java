package pl.medical.visits.util;

import pl.medical.visits.model.entity.user.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;

public class ValidationUtil {
    private static final String BIRTHDAY_REGEX = "^(\\d{4})(-)([0-1][1-9])(-)([0-3]\\d)$";
    private static final String PHONE_NR_REGEX = "\\d{9,11}";
    private static final String PESEL_REGEX = "\\d{11}";
    private ValidationUtil() {}
    public static String firstCapital(String str) {
        return str.substring(0, 1).toUpperCase(Locale.ROOT)
                + str.substring(1).toLowerCase();
    }

    public static boolean isStringNotNull(String string) {
        if(string == null) return false;
        return !string.isEmpty();
    }

    public static boolean isBirthDayInvalid(String birthday) {
        return birthday.matches(BIRTHDAY_REGEX);
    }

    public static boolean isPeselValid(User user) {
        String pesel = user.getPesel();
        String sex = user.getSex();

        if (pesel.length() != 11) return false;

        char[] pArray = pesel.toCharArray();

        if (!isBirthDayEqualPesel(user)) return false;
        if (!isPeselSumValid(pArray)) return false;
        if(pArray[10] % 2 == 0 && sex.equals("Female")) return true;
        return pArray[10] % 2 != 0 && sex.equals("Male");
    }

    private static boolean isPeselSumValid(char[] pArray) {
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

    private static boolean isBirthDayEqualPesel(User user) {
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
