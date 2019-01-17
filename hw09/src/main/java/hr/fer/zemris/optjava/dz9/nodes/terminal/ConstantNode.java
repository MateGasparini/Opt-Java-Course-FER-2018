package hr.fer.zemris.optjava.dz9.nodes.terminal;

import hr.fer.zemris.optjava.dz9.nodes.SymbolicNode;

public class ConstantNode extends TerminalNode {

    public ConstantNode(double value) {
        this.value = value;
    }

    @Override
    public double calculateValue() {
        return value;
    }

    @Override
    public SymbolicNode copy() {
        SymbolicNode copy = new ConstantNode(value);
        copy.setDepth(getDepth());
        copy.setSize(getSize());
        copy.setSubTreeDepth(getSubTreeDepth());
        return copy;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
