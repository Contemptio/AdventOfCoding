package mmxvii.dec07;

import java.io.File;
import java.io.IOException;
import java.util.List;

import util.FileUtil;
import util.RunUtil;

public class ProgramAnalyzer {
    private Computer computer;

    public ProgramAnalyzer(List<String> lines, int part) {
        computer = new Computer(lines);
    }

    private String root() {
        return computer.root().name();
    }

    public static void main(String[] args) throws IOException {
        if (args.length <= 1) {
            RunUtil.error("Missing arguments: <fileName> <part>");
        }

        int part = 0;
        try {
            part = Integer.parseInt(args[1]);
        } catch (NumberFormatException error) {
            RunUtil.error("Second argument must be an integer: " + args[1] + ".");
        }

        ProgramAnalyzer analyzer = new ProgramAnalyzer(FileUtil.fileAsLines(new File(args[0])), part);
        System.out.println("Root program: " + analyzer.root());
    }

}
