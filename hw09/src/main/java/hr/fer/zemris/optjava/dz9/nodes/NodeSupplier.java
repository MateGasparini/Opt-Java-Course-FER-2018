package hr.fer.zemris.optjava.dz9.nodes;

import hr.fer.zemris.optjava.dz9.nodes.bifunction.AddNode;
import hr.fer.zemris.optjava.dz9.nodes.bifunction.DivNode;
import hr.fer.zemris.optjava.dz9.nodes.bifunction.MulNode;
import hr.fer.zemris.optjava.dz9.nodes.bifunction.SubNode;
import hr.fer.zemris.optjava.dz9.nodes.function.*;
import hr.fer.zemris.optjava.dz9.nodes.terminal.ConstantNode;
import hr.fer.zemris.optjava.dz9.nodes.terminal.VariableNode;

import java.util.*;
import java.util.function.Supplier;

public class NodeSupplier {

    private static NodeSupplier instance = new NodeSupplier();

    private Random random = new Random();

    private List<Supplier<SymbolicNode>> nonTerminal = new ArrayList<>();

    private List<Supplier<SymbolicNode>> terminal = new ArrayList<>();

    private List<Supplier<SymbolicNode>> all = new ArrayList<>();

    private Map<String, Integer> variableIndexes = new HashMap<>();

    private NodeSupplier() {
    }

    public static void allowFunctionNodes(String[] nodeNames) {
        for (String name : nodeNames) {
            if (name.equals("+")) allowFunctionNode(AddNode::new);
            else if (name.equals("-")) allowFunctionNode(SubNode::new);
            else if (name.equals("*")) allowFunctionNode(MulNode::new);
            else if (name.equals("/")) allowFunctionNode(DivNode::new);
            else if (name.equals("sin")) allowFunctionNode(SinNode::new);
            else if (name.equals("cos")) allowFunctionNode(CosNode::new);
            else if (name.equals("sqrt")) allowFunctionNode(SqrtNode::new);
            else if (name.equals("log")) allowFunctionNode(LogNode::new);
            else if (name.equals("exp")) allowFunctionNode(ExpNode::new);
        }
    }

    public static void allowConstantNodes(double min, double max) {
        allowTerminalNode(() -> new ConstantNode(instance.randomInRange(min, max)));
    }

    public static void allowVariableNodes(String[] names) {
        for (int i = 0; i < names.length - 1; i ++) {
            String name = names[i];
            instance.variableIndexes.put(name, i);
            allowTerminalNode(() -> new VariableNode(name));
        }
    }

    public static SymbolicNode getNonTerminalNode() {
        return instance.randomNodeFrom(instance.nonTerminal);
    }

    public static SymbolicNode getTerminalNode() {
        return instance.randomNodeFrom(instance.terminal);
    }

    public static SymbolicNode getNode() {
        return instance.randomNodeFrom(instance.all);
    }

    public static int getVariableIndex(String name) {
        return instance.variableIndexes.get(name);
    }

    private static void allowFunctionNode(Supplier<SymbolicNode> supplier) {
        instance.nonTerminal.add(supplier);
        instance.all.add(supplier);
    }

    private static void allowTerminalNode(Supplier<SymbolicNode> supplier) {
        instance.terminal.add(supplier);
        instance.all.add(supplier);
    }

    private SymbolicNode randomNodeFrom(List<Supplier<SymbolicNode>> suppliers) {
        return suppliers.get(random.nextInt(suppliers.size())).get();
    }

    private double randomInRange(double min, double max) {
        return min + random.nextDouble() * (max - min);
    }
}
