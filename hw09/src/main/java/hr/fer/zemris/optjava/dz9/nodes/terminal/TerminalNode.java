package hr.fer.zemris.optjava.dz9.nodes.terminal;

import hr.fer.zemris.optjava.dz9.nodes.SymbolicNode;

public abstract class TerminalNode extends SymbolicNode {

    protected double value;

    @Override
    public int updateSize() {
        setSize(1);
        return 1;
    }

    @Override
    public int updateSubTreeDepth() {
        setSubTreeDepth(0);
        return 0;
    }

    @Override
    public void updateDepth(int depth) {
        setDepth(depth);
    }
}
