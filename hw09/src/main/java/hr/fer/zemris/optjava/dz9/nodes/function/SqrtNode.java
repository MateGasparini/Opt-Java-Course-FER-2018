package hr.fer.zemris.optjava.dz9.nodes.function;

public class SqrtNode extends FunctionNode {

    public SqrtNode() {
        super(a -> a >= 0.0 ? Math.sqrt(a) : SAFE_VALUE);
    }

    @Override
    public String toString() {
        return "sqrt(" + getChild().toString() + ")";
    }
}
