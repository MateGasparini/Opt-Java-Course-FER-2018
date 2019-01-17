package hr.fer.zemris.optjava.dz9.nodes.function;

public class ExpNode extends FunctionNode {

    public ExpNode() {
        super(a -> {
            double exp = Math.exp(a);
            return Double.isFinite(exp) ? exp : SAFE_VALUE;
        });
    }

    @Override
    public String toString() {
        return "exp(" + getChild().toString() + ")";
    }
}
