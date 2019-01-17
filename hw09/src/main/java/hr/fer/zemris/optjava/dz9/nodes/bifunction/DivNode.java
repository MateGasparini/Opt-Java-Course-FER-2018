package hr.fer.zemris.optjava.dz9.nodes.bifunction;

import hr.fer.zemris.optjava.dz9.nodes.SymbolicNode;

public class DivNode extends BiFunctionNode {

    public DivNode() {
        super((a, b) -> b != 0.0 ? a / b : SAFE_VALUE);
    }

    @Override
    public String toString() {
        SymbolicNode[] children = getChildren();
        return "div(" + children[0].toString() + ", " + children[1].toString() + ")";
    }
}
