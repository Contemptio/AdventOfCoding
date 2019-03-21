package mmxvii.dec07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.CollectionUtil;
import util.TreeNode;

public class Computer {
    private Map<String, TreeNode<Process>> processMap;
    private TreeNode<Process> root;

    public Computer(List<String> lines) {
        this.processMap = new HashMap<String, TreeNode<Process>>();
        for (String line : lines) {
            String[] sides = line.split("\\s*->\\s*");

            String[] lhs = sides[0].split("\\s+");
            String name = lhs[0];
            int weight = Integer.parseInt(lhs[1].replaceAll("\\(|\\)", ""));
            List<String> names = new ArrayList<String>();

            if (sides.length > 1) {
                names.addAll(CollectionUtil.newList(sides[1].split("\\s*")));
            }
            processMap.put(name, new TreeNode<Process>(new Process(name, weight, names)));
        }

        for (Map.Entry<String, TreeNode<Process>> entryNode : processMap.entrySet()) {
            TreeNode<Process> node = entryNode.getValue();
            Process process = node.value();

            if (process.hasChildren()) {
                for (String childName : process.children()) {
                    TreeNode<Process> child = processMap.get(childName);
                    node.addChild(child);
                    child.setParent(node);
                }
            }
        }

        for (TreeNode<Process> node : processMap.values()) {
            if (node.isRoot()) {
                this.root = node;
                break;
            }
        }
    }

    public Process root() {
        return root.value();
    }

}
