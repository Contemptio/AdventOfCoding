package mmxvii.dec02;

import java.io.File;
import java.io.IOException;
import java.util.List;

import util.FileUtil;
import util.RunUtil;

public class CorruptionChecksum {

    public static void main(String[] args) throws IOException {
        if (args.length <= 0) {
            RunUtil.error("Usage: CorruptionChecksum <file>");
        }
        File file = new File(args[0]);
        if (!file.exists()) {
            RunUtil.error("File " + file + " does not exist.");
        }

        CorruptionChecksum program = new CorruptionChecksum(file);
        System.out.println(program.getChecksum());
    }

    private List<List<String>> cells;

    public CorruptionChecksum(File source) throws IOException {
        cells = FileUtil.fileAsMatrix(source);
    }

    public int getChecksum() {
        return cells.stream().mapToInt(list -> maxDiff(list)).sum();
    }

    private int maxDiff(List<String> list) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (String cell : list) {
            int number = Integer.parseInt(cell);
            min = min > number ? number : min;
            max = max < number ? number : max;
        }

        return max - min;
    }
}
