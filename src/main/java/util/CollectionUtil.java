package util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CollectionUtil {

    private CollectionUtil() {
    }

    public static <T, V> List<List<V>> transformNestedElements(List<List<T>> lists, Function<T, V> function) {

        return transformNested(lists, list -> list.stream().<V>map(function).collect(Collectors.toList()));
    }

    public static <T, V> List<List<V>> transformNested(List<List<T>> lists, Function<List<T>, List<V>> transformer) {

        return lists.stream().<List<V>>map(transformer).collect(Collectors.toList());
    }

    @SafeVarargs
    public static <T> List<T> newList(T... elements) {
        List<T> list = new ArrayList<T>();
        for (T element : elements) {
            list.add(element);
        }
        return list;
    }
}
