package hr.fer.zemris.optjava.dz9.nodes;

import java.util.List;

public abstract class SymbolicNode {

    protected static final double SAFE_VALUE = 1.0;

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

    public abstract double calculateValue();

    public abstract SymbolicNode copy();

    public abstract int updateSize();

    public abstract int updateSubTreeDepth();

    public abstract void updateDepth(int depth);

    public void fillList(List<SymbolicNode> treeList) {
        treeList.add(this);
    }
}
