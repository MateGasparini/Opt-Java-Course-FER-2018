package hr.fer.zemris.optjava.dz9.nodes.terminal;

import hr.fer.zemris.optjava.dz9.nodes.SymbolicNode;

public class VariableNode extends TerminalNode {

    private String id;

    public VariableNode(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double calculateValue() {
        return value;
    }

    @Override
    public SymbolicNode copy() {
        SymbolicNode copy = new VariableNode(id);
        copy.setDepth(getDepth());
        copy.setSize(getSize());
        copy.setSubTreeDepth(getSubTreeDepth());
        return copy;
    }

    @Override
    public String toString() {
        return id;
    }
}
