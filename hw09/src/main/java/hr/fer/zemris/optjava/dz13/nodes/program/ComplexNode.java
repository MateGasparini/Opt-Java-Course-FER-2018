package hr.fer.zemris.optjava.dz13.nodes.program;

import hr.fer.zemris.optjava.dz13.nodes.Node;

public abstract class ComplexNode extends Node {

    protected Node[] children;

    public Node[] getChildren() {
        return children;
    }

    @Override
    public int updateSize() {
        int size = 1;
        for (Node child : children) {
            size += child.updateSize();
        }
        setSize(size);
        return size;
    }

    @Override
    public int updateSubTreeDepth() {
        int maxDepth = 0;
        for (Node child : children) {
            int depth = child.updateSubTreeDepth();
            if (depth > maxDepth) maxDepth = depth;
        }
        maxDepth ++;
        setSubTreeDepth(maxDepth);
        return maxDepth;
    }

    @Override
    public void updateDepth(int depth) {
        setDepth(depth);
        depth ++;
        for (Node child : children) {
            child.updateDepth(depth);
        }
    }
}
