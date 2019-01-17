package hr.fer.zemris.optjava.dz9.nodes.bifunction;

import hr.fer.zemris.optjava.dz9.nodes.SymbolicNode;

import java.util.List;
import java.util.function.BiFunction;

public abstract class BiFunctionNode extends SymbolicNode {

    private BiFunction<Double, Double, Double> biFunction;

    private SymbolicNode[] children = new SymbolicNode[2];

    public BiFunctionNode(BiFunction<Double, Double, Double> biFunction) {
        this.biFunction = biFunction;
    }

    public SymbolicNode[] getChildren() {
        return children;
    }

    public void setChildren(SymbolicNode first, SymbolicNode second) {
        children[0] = first;
        first.setDepth(getDepth() + 1);
        children[1] = second;
        second.setDepth(getDepth() + 1);
    }

    @Override
    public double calculateValue() {
        return biFunction.apply(children[0].calculateValue(), children[1].calculateValue());
    }

    @Override
    public SymbolicNode copy() {
        try {
            BiFunctionNode copy = getClass().getConstructor().newInstance();
            copy.children[0] = this.children[0].copy();
            copy.children[1] = this.children[1].copy();
            copy.setDepth(getDepth());
            copy.setSize(getSize());
            copy.setSubTreeDepth(getSubTreeDepth());
            return copy;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public int updateSize() {
        int size = children[0].updateSize() + children[1].updateSize() + 1;
        setSize(size);
        return size;
    }

    @Override
    public int updateSubTreeDepth() {
        int leftDepth = children[0].updateSubTreeDepth();
        int rightDepth = children[1].updateSubTreeDepth();
        int depth = Math.max(leftDepth, rightDepth) + 1;
        setSubTreeDepth(depth);
        return depth;
    }

    @Override
    public void updateDepth(int depth) {
        setDepth(depth);
        depth ++;
        children[0].updateDepth(depth);
        children[1].updateDepth(depth);
    }

    @Override
    public void fillList(List<SymbolicNode> treeList) {
        super.fillList(treeList);
        children[0].fillList(treeList);
        children[1].fillList(treeList);
    }
}
