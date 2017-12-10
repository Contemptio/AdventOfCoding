package util;

public class Coords implements Comparable<Coords> {
    public int x;
    public int y;

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int distance(Coords other) {
        return Math.abs(other.x - x) + Math.abs(other.y - y);
    }

    @Override
    public int compareTo(Coords other) {
        return distance(other);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Coords)) {
            return false;
        }
        return compareTo((Coords) other) == 0;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
