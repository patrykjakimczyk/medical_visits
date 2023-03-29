package pl.medical.visits.util;

import pl.medical.visits.model.user.User;

import java.util.Locale;

public class ValidationUtil {
    public static String firstCapital(String str) {
        return str.substring(0, 1).toUpperCase(Locale.ROOT)
                + str.substring(1).toLowerCase();
    }

    public static boolean isPeselValid(User user) {
        String pesel = user.getPesel();
        String sex = user.getSex();

        if (pesel.length() != 11) return false;

        char[] pArray = pesel.toCharArray();

        if (!isPeselSumValid(pArray)) return false;
        if(pArray[10] % 2 == 0 && sex.equals("female")) return true;
        return pArray[10] % 2 != 0 && sex.equals("male");
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
}
