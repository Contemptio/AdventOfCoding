package util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class FileUtil {

    private FileUtil() {}

    public static List<List<String>> fileAsMatrix(File file)
            throws IOException {

        return fileAsLines(file).stream()
                .map(line -> Arrays.asList(line.split("\\s+")))
                .collect(Collectors.toList());
    }

    public static List<String> fileAsLines(File file) throws IOException {
        return Arrays.asList(fileAsString(file).split("\\s*\\n\\s*"));
    }

    private static String fileAsString(File file) throws IOException {
        return new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())),
                StandardCharsets.UTF_8);
    }
}
