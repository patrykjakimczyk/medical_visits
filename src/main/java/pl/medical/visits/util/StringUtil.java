package pl.medical.visits.util;

import java.util.Locale;

public class StringUtil {

    public static String firstCapital(String str) {
        return str.substring(0, 1).toUpperCase(Locale.ROOT)
                + str.substring(1).toLowerCase();
    }

    public static boolean isLengthOverMax(String string, int max) {
        return string.length() > max;
    }

    public static boolean isStringNotBlank(String string) {
        if(string == null) return false;
        return !string.isEmpty();
    }
}
