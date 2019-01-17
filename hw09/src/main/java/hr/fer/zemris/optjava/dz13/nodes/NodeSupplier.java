package hr.fer.zemris.optjava.dz13.nodes;

import hr.fer.zemris.optjava.dz13.nodes.action.ActionNode;
import hr.fer.zemris.optjava.dz13.nodes.action.LeftNode;
import hr.fer.zemris.optjava.dz13.nodes.action.MoveNode;
import hr.fer.zemris.optjava.dz13.nodes.action.RightNode;
import hr.fer.zemris.optjava.dz13.nodes.program.ComplexNode;
import hr.fer.zemris.optjava.dz13.nodes.program.IfFoodAheadNode;
import hr.fer.zemris.optjava.dz13.nodes.program.Prog2Node;
import hr.fer.zemris.optjava.dz13.nodes.program.Prog3Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class NodeSupplier {

    private static NodeSupplier instance = new NodeSupplier();

    private Random random = new Random();

    private List<Supplier<ComplexNode>> nonTerminal = List.of(
            IfFoodAheadNode::new, Prog2Node::new, Prog3Node::new
    );

    private List<Supplier<ActionNode>> terminal = List.of(
            MoveNode::new, LeftNode::new, RightNode::new
    );

    private List<Supplier<? extends Node>> all = new ArrayList<>();

    private NodeSupplier() {
        all.addAll(nonTerminal);
        all.addAll(terminal);
    }

    public static ComplexNode getNonTerminalNode() {
        return instance.randomNodeFrom(instance.nonTerminal);
    }

    public static ActionNode getTerminalNode() {
        return instance.randomNodeFrom(instance.terminal);
    }

    public static Node getNode() {
        return instance.all.get(instance.random.nextInt(instance.all.size())).get();
    }

    private <T extends Node> T randomNodeFrom(List<Supplier<T>> suppliers) {
        return suppliers.get(random.nextInt(suppliers.size())).get();
    }
}
