package hr.fer.zemris.optjava.dz13.nodes;

import java.util.List;

public abstract class Node {

    private int depth;

    private int size;

    private int subTreeDepth;

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSubTreeDepth() {
        return subTreeDepth;
    }

    public void setSubTreeDepth(int subTreeDepth) {
        this.subTreeDepth = subTreeDepth;
    }

    public abstract Node copy();

    public abstract int updateSize();

    public abstract int updateSubTreeDepth();

    public abstract void updateDepth(int depth);

    public void fillList(List<Node> treeList) {
        treeList.add(this);
    }
}
