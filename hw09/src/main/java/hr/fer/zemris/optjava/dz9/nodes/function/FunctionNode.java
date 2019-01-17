package hr.fer.zemris.optjava.dz9.nodes.function;

import hr.fer.zemris.optjava.dz9.nodes.SymbolicNode;

import java.util.List;
import java.util.function.Function;

public abstract class FunctionNode extends SymbolicNode {

    private Function<Double, Double> function;

    private SymbolicNode child;

    public FunctionNode(Function<Double, Double> function) {
        this.function = function;
    }

    public SymbolicNode getChild() {
        return child;
    }

    public void setChild(SymbolicNode child) {
        this.child = child;
        child.setDepth(getDepth() + 1);
    }

    @Override
    public double calculateValue() {
        return function.apply(child.calculateValue());
    }

    @Override
    public SymbolicNode copy() {
        try {
            FunctionNode copy = getClass().getConstructor().newInstance();
            copy.child = this.child.copy();
            copy.setDepth(getDepth());
            copy.setSize(getSize());
            copy.setSubTreeDepth(getSubTreeDepth());
            return copy;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int updateSize() {
        int size = child.updateSize() + 1;
        setSize(size);
        return size;
    }

    @Override
    public int updateSubTreeDepth() {
        int depth = child.updateSubTreeDepth() + 1;
        setSubTreeDepth(depth);
        return depth;
    }

    @Override
    public void updateDepth(int depth) {
        setDepth(depth);
        depth ++;
        child.updateDepth(depth);
    }

    @Override
    public void fillList(List<SymbolicNode> treeList) {
        super.fillList(treeList);
        child.fillList(treeList);
    }
}
