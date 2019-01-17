package hr.fer.zemris.optjava.dz13.algorithm.mutation;

import hr.fer.zemris.optjava.dz13.algorithm.solution.TreeSolution;
import hr.fer.zemris.optjava.dz13.nodes.Node;
import hr.fer.zemris.optjava.dz13.nodes.NodeSupplier;
import hr.fer.zemris.optjava.dz13.nodes.action.ActionNode;
import hr.fer.zemris.optjava.dz13.nodes.program.Complex2Node;
import hr.fer.zemris.optjava.dz13.nodes.program.Complex3Node;

import java.util.List;
import java.util.Random;

/**
 * Mutation which replaces some random node from the given parent's copy with a
 * randomly generated sub tree.
 *
 * @author Mate Gašparini
 */
public class SubTreeMutation implements Mutation<TreeSolution> {

    /** Single {@link Random} instance. */
    private Random random = new Random();

    /** Maximum allowed tree depth. */
    private int maxDepth;

    /** Maximum allowed tree node count. */
    private int maxNodes;

    /** Current tree node count. */
    private int nodeCount;

    /**
     * Constructor specifying the maximum depth and the maximum node count.
     *
     * @param maxDepth The maximum allowed tree depth.
     * @param maxNodes The maximum allowed tree node count.
     */
    public SubTreeMutation(int maxDepth, int maxNodes) {
        this.maxDepth = maxDepth;
        this.maxNodes = maxNodes;
    }

    @Override
    public TreeSolution reproduce(TreeSolution parent) {
        TreeSolution mutated = parent.copy();
        nodeCount = 1;
        List<Node> treeList = mutated.getTreeList();
        Node node;
        do {
            node = treeList.get(random.nextInt(treeList.size()));
        } while (node instanceof ActionNode);
        mutateNode(node);

        mutated.updateSizes();
        mutated.updateSubTreeDepths();
        return mutated;
    }

    private void mutateNode(Node node) {
        if (node instanceof Complex2Node) {
            Node first;
            Node second;
            if (node.getDepth() >= maxDepth-1 || nodeCount >= maxNodes-1) {
                first = NodeSupplier.getTerminalNode();
                second = NodeSupplier.getTerminalNode();
            } else {
                first = NodeSupplier.getNode();
                second = NodeSupplier.getNode();
            }
            nodeCount += 2;

            ((Complex2Node) node).setChildren(first, second);
            mutateNode(first);
            mutateNode(second);
        } else if (node instanceof Complex3Node) {
            Node first;
            Node second;
            Node third;
            if (node.getDepth() >= maxDepth-2 || nodeCount >= maxNodes-2) {
                first = NodeSupplier.getTerminalNode();
                second = NodeSupplier.getTerminalNode();
                third = NodeSupplier.getTerminalNode();
            } else {
                first = NodeSupplier.getNode();
                second = NodeSupplier.getNode();
                third = NodeSupplier.getNode();
            }
            nodeCount += 3;

            ((Complex3Node) node).setChildren(first, second, third);
            mutateNode(first);
            mutateNode(second);
            mutateNode(third);
        }
    }
}
