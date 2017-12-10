package mmxvii.dec05;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import util.FileUtil;
import util.RunUtil;

public class InstructionJumping {
    private int pointer;
    private int stepsTaken;
    private List<Integer> steps;
    private Function<Integer, Integer> newValue;

    public InstructionJumping(List<Integer> steps, int part) {
        this.steps = steps;
        this.stepsTaken = 0;
        this.pointer = 0;
        this.newValue = selectFunction(part);
    }

    public int solve() {
        while (step()) {
            stepsTaken++;
        }
        return stepsTaken;
    }

    public boolean step() {
        if (pointer >= steps.size()) {
            return false;
        }
        int jumpLength = steps.get(pointer);
        int newPos = pointer + jumpLength;
        steps.set(pointer, newValue.apply(jumpLength));
        pointer = newPos;
        return true;
    }

    private Function<Integer, Integer> selectFunction(int part) {
        switch (part) {
            case 1:
                return x -> x + 1;
            case 2:
                return x -> x + (x >= 3 ? -1 : 1);
            default:
                throw new IllegalArgumentException("Invalid part: " + part + "!");
        }
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

        InstructionJumping counter = new InstructionJumping(FileUtil.fileAsLines(new File(args[0])).stream()
                .mapToInt(Integer::parseInt).boxed().collect(Collectors.toList()), part);
        System.out.println(counter.solve());
    }
}
