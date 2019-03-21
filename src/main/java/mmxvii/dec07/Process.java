package mmxvii.dec07;

import java.util.List;

import util.CollectionUtil;

public class Process {
    private String name;
    private int weight;
    private List<String> childrenNames;

    public Process(String name, int weight, String... childrenNames) {
        this(name, weight, CollectionUtil.newList(childrenNames));
    }

    public Process(String name, int weight, List<String> childrenNames) {
        this.name = name;
        this.weight = weight;
        this.childrenNames = childrenNames;
    }

    public boolean hasChildren() {
        return !childrenNames.isEmpty();
    }

    public String name() {
        return name;
    }

    public List<String> children() {
        return childrenNames;
    }

    public int weight() {
        return weight;
    }
}
