package mmxvii.dec06;

import java.util.ArrayList;
import java.util.List;

import util.NumberUtil;
import util.StringUtil;

public class MemoryBank {
    private List<Memory> configurations;
    private int cycleLength;

    public MemoryBank(List<Integer> initialValues) {
        this.configurations = new ArrayList<Memory>();
        this.configurations.add(new Memory(initialValues));

        Memory nextConfiguration = redistribute();
        do {
            this.configurations.add(nextConfiguration);
            nextConfiguration = redistribute();
        } while (!hasConfiguration(nextConfiguration));
    }

    public int firstRepetition() {
        return configurations.size() - 1;
    }

    public int size() {
        return configurations.size();
    }

    public int loopLength() {
        return cycleLength;
    }

    private Memory redistribute() {
        Memory memory = configurations.get(configurations.size() - 1).copy();
        Cell highest = memory.highest();
        List<Integer> partition = NumberUtil.distribute(highest.value(), memory.size());
        int highestIndex = highest.index();
        memory.nullify(highestIndex);
        memory.addCircular(partition, highestIndex + 1);
        return memory;
    }

    private boolean hasConfiguration(Memory memory) {
        for (int i = 0; i < configurations.size(); ++i) {
            if (memory.equals(configurations.get(i))) {
                cycleLength = size() - i;
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return StringUtil.verticalString(configurations);
    }
}
