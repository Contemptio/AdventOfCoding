package mmxvii.dec06;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.NumberUtil;

public class Memory implements Comparable<Memory> {
    private static final Memory EMPTY = new Memory(Collections.emptyList());
    private List<Integer> cells;

    public Memory(List<Integer> initialValues) {
        this.cells = initialValues;
    }

    public static Memory empty() {
        return EMPTY;
    }

    public void add(List<Integer> partition) {
        add(new Memory(partition));
    }

    public void add(Memory old) {
        NumberUtil.add(cells, old.cells);
    }

    public int get(int index) {
        return cells.get(index);
    }

    public Cell highest() {
        int index = NumberUtil.indexOfHighest(cells);
        int value = cells.get(index);
        return new Cell(index, value);
    }

    public int size() {
        return cells.size();
    }

    public boolean nullify(int index) {
        if (index >= size()) {
            return false;
        }
        cells.set(index, 0);
        return true;
    }

    public Memory copy() {
        return new Memory(new ArrayList<Integer>(cells));
    }

    @Override
    public int compareTo(Memory other) {
        int size = size();
        int cmp = size - other.size();
        if (cmp != 0) {
            return cmp;
        }

        for (int i = 0; i < size; ++i) {
            if ((cmp = get(i) - other.get(i)) != 0) {
                return cmp;
            }
        }

        return cmp;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Memory)) {
            return false;
        }
        return cells.equals(((Memory) other).cells);
    }

    @Override
    public String toString() {
        return cells.toString();
    }

    public void addCircular(List<Integer> partition, int start) {
        int size = size();

        for (int value : partition) {
            start %= size;
            cells.set(start, cells.get(start++) + value);
        }
    }

}
