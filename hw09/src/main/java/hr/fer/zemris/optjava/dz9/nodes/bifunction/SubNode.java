package hr.fer.zemris.optjava.dz9.nodes.bifunction;

import hr.fer.zemris.optjava.dz9.nodes.SymbolicNode;

public class SubNode extends BiFunctionNode {

    public SubNode() {
        super((a, b) -> a - b);
    }

    @Override
    public String toString() {
        SymbolicNode[] children = getChildren();
        return "sub(" + children[0].toString() + ", " + children[1].toString() + ")";
    }
}
