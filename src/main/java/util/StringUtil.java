package util;

import java.util.Arrays;
import java.util.List;
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

    public static List<Integer> parseIntegerList(List<String> list) {
        return list.stream().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
    }

    public static List<List<Integer>> parseIntegerMatrix(List<List<String>> matrix) {
        return matrix.stream().map(StringUtil::parseIntegerList).collect(Collectors.toList());
    }

    public static String verticalString(List<?> configurations) {
        StringBuilder string = new StringBuilder();
        String prefix = "";

        for (Object object : configurations) {
            string.append(prefix + object.toString());
            prefix = "\n";
        }
        return string.toString();
    }
}
