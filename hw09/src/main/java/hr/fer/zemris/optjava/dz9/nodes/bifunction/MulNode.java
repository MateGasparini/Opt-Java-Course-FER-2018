package hr.fer.zemris.optjava.dz9.nodes.bifunction;

import hr.fer.zemris.optjava.dz9.nodes.SymbolicNode;

public class MulNode extends BiFunctionNode {

    public MulNode() {
        super((a, b) -> a * b);
    }

    @Override
    public String toString() {
        SymbolicNode[] children = getChildren();
        return "mul(" + children[0].toString() + ", " + children[1].toString() + ")";
    }
}
