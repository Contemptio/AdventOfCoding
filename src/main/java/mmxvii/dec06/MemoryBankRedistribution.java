package mmxvii.dec06;

import java.io.File;
import java.io.IOException;
import java.util.List;

import util.FileUtil;
import util.RunUtil;

public class MemoryBankRedistribution {

    private MemoryBank bank;

    public MemoryBankRedistribution(List<Integer> initialValues, int part) {
        this.bank = new MemoryBank(initialValues);
    }

    private int numberOfCycles() {
        return this.bank.size();
    }

    private int loopLength() {
        return bank.loopLength();
    }

    @Override
    public String toString() {
        return bank.toString();
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

        MemoryBankRedistribution distributor = new MemoryBankRedistribution(
                FileUtil.fileAsIntegers(new File(args[0])).get(0), part);

        System.out.println("Cycles generated: " + distributor.numberOfCycles());
        System.out.println("Loop length: " + distributor.loopLength());
    }
}
