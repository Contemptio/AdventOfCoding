package util;

import java.io.File;

public final class RunUtil {

    private RunUtil() {}

    public static void error(String message) {
        System.err.println(message);
        System.exit(1);
    }

    public static File getFile(String[] args, int pos) {
        if (args.length >= pos) {
            error("Too few arguments: expected file argument at position "
                    + (pos + 1) + ".");
        }
        return new File(args[pos]);
    }
}
