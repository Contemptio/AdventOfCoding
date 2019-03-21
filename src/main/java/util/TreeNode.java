package util;

import java.util.ArrayList;
import java.util.List;

public class TreeNode<T extends Object> {
    protected T value;
    protected TreeNode<T> parent;
    protected List<TreeNode<T>> children;

    private TreeNode() {
    }

    public TreeNode(T value) {
        this(null, new ArrayList<TreeNode<T>>(), value);
        this.parent = new NullNode<T>();
    }

    @SafeVarargs
    public TreeNode(TreeNode<T> parent, T value, TreeNode<T>... children) {
        this(parent, CollectionUtil.newList(children), value);
    }

    public TreeNode(TreeNode<T> parent, List<TreeNode<T>> children, T value) {
        this.parent = parent;
        this.value = value;
        this.children = children;
    }

    public boolean isLeaf() {
        return children.isEmpty();
    }

    public List<TreeNode<T>> children() {
        return children;
    }

    public boolean isNull() {
        return false;
    }

    public T value() {
        return value;
    }

    private class NullNode<V extends T> extends TreeNode<T> {

        public boolean isNull() {
            return true;
        }
    }

    public void addChild(TreeNode<T> treeNode) {
        children.add(treeNode);
    }

    public void setParent(TreeNode<T> newParent) {
        this.parent = newParent;
    }

    public boolean isRoot() {
        // TODO Auto-generated method stub
        return false;
    }
}
