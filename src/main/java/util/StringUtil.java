package util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public final class StringUtil {

    private StringUtil() {
    }

    public static String format(String string, int newLength) {
        int length = string.length();
        if (length >= newLength) {
            return string;
        }
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < newLength - length; ++i) {
            spaces.append(" ");
        }
        return string + spaces.toString();
    }

    public static Map<String, Long> charCount(String word) {
        return Arrays.stream(word.toLowerCase().split(""))
                .collect(Collectors.groupingBy(str -> str, Collectors.counting()));
    }

    public static boolean isAnagramOf(String reference, String word) {
        return charCount(reference).equals(charCount(word));
    }
}
