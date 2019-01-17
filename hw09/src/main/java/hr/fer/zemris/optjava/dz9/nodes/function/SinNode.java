package hr.fer.zemris.optjava.dz9.nodes.function;

public class SinNode extends FunctionNode {

    public SinNode() {
        super(Math::sin);
    }

    @Override
    public String toString() {
        return "sin(" + getChild().toString() + ")";
    }
}
