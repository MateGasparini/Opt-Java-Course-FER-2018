package hr.fer.zemris.optjava.dz13.nodes.action;

import hr.fer.zemris.optjava.dz13.nodes.Node;

public abstract class ActionNode extends Node {

    @Override
    public Node copy() {
        try {
            return getClass().getConstructor().newInstance();
        } catch (Exception ex) {
            return null;
        }
    }

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
