package hr.fer.zemris.optjava.dz9.nodes.function;

public class LogNode extends FunctionNode {

    public LogNode() {
        super(a -> a > 0 ? Math.log10(a) : SAFE_VALUE);
    }

    @Override
    public String toString() {
        return "log(" + getChild().toString() + ")";
    }
}
