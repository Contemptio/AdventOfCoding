package util;

public final class RunUtil {

    private RunUtil() {}

    public static void error(String message) {
        System.err.println(message);
        System.exit(1);
    }
}
