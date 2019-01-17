package hr.fer.zemris.optjava.dz9.nodes.function;

public class CosNode extends FunctionNode {

    public CosNode() {
        super(Math::cos);
    }

    @Override
    public String toString() {
        return "cos(" + getChild().toString() + ")";
    }
}
