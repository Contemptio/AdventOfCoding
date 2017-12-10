package mmxvii.dec06;

public class Cell {

    private int index;
    private int value;

    public Cell(int index, int value) {
        this.index = index;
        this.value = value;
    }

    public int index() {
        return index;
    }

    public int value() {
        return value;
    }
}