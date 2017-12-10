package util;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public final class NumberUtil {
    private NumberUtil() {
    }

    public static int indexOfHighest(List<Integer> integers) {
        OptionalInt maybeInt = IntStream.range(0, integers.size())
                .reduce((a, b) -> integers.get(a) < integers.get(b) ? b : a);
        if (maybeInt.isPresent()) {
            return maybeInt.getAsInt();
        }
        return -1;
    }

    public static List<Integer> distribute(int integer, int parts) {
        int base = integer / parts;
        int remainder = integer % parts;
        List<Integer> partition = new ArrayList<Integer>(parts);

        for (int i = 0; i < parts; ++i) {
            partition.add(base + (remainder-- > 0 ? 1 : 0));
        }
        return partition;
    }

    public static void add(List<Integer> destination, List<Integer> source) {
        for (int i = 0; i < Math.min(source.size(), destination.size()); ++i) {
            destination.set(i, destination.get(i) + source.get(i));
        }
    }
}
