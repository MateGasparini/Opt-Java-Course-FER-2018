package hr.fer.zemris.optjava.dz9.nodes.bifunction;

import hr.fer.zemris.optjava.dz9.nodes.SymbolicNode;

public class AddNode extends BiFunctionNode {

    public AddNode() {
        super((a, b) -> a + b);
    }

    @Override
    public String toString() {
        SymbolicNode[] children = getChildren();
        return "add(" + children[0].toString() + ", " + children[1].toString() + ")";
    }
}
