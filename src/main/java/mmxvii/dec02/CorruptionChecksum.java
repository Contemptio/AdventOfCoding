package mmxvii.dec02;

import java.io.File;
import java.io.IOException;
import java.util.List;

import util.CollectionUtil;
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
        if (args.length <= 1) {
            System.out.println(program.getChecksum());
        } else {
            System.out.println(program.getRowDivisions());
        }

    }

    private List<List<Integer>> cells;

    public CorruptionChecksum(File source) throws IOException {
        this.cells = CollectionUtil.transformNestedElements(
                FileUtil.fileAsMatrix(source), Integer::parseInt);
    }

    public int getRowDivisions() {
        return cells.stream().mapToInt(list -> divide(list)).sum();
    }

    private int divide(List<Integer> list) {
        for (int i = 0; i < list.size(); ++i) {
            for (int j = 0; j < list.size(); ++j) {
                if (i == j) {
                    continue;
                }
                int x = list.get(i);
                int y = list.get(j);

                if (x % y == 0) {
                    return x / y;
                } else if (y % x == 0) {
                    return y / x;
                }
            }
        }
        return 0;
    }

    public int getChecksum() {
        return cells.stream().mapToInt(list -> maxDiff(list)).sum();
    }

    private int maxDiff(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int number : list) {
            min = min > number ? number : min;
            max = max < number ? number : max;
        }

        return max - min;
    }
}
