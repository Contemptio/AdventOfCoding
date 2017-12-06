package util;

public final class StringUtil {

    private StringUtil() {}

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
}
