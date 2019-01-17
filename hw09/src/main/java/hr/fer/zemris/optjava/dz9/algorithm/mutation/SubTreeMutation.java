package hr.fer.zemris.optjava.dz9.algorithm.mutation;

import hr.fer.zemris.optjava.dz9.algorithm.solution.SymbolicTreeSolution;
import hr.fer.zemris.optjava.dz9.nodes.NodeSupplier;
import hr.fer.zemris.optjava.dz9.nodes.SymbolicNode;
import hr.fer.zemris.optjava.dz9.nodes.bifunction.BiFunctionNode;
import hr.fer.zemris.optjava.dz9.nodes.function.FunctionNode;
import hr.fer.zemris.optjava.dz9.nodes.terminal.TerminalNode;

import java.util.List;
import java.util.Random;

/**
 * Mutation which replaces some random node from the given parent's copy with a
 * randomly generated sub tree.
 *
 * @author Mate Gašparini
 */
public class SubTreeMutation implements Mutation<SymbolicTreeSolution> {

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
    public SymbolicTreeSolution reproduce(SymbolicTreeSolution parent) {
        SymbolicTreeSolution mutated = parent.copy();
        nodeCount = 1;
        List<SymbolicNode> treeList = mutated.getTreeList();
        SymbolicNode node;
        do {
            node = treeList.get(random.nextInt(treeList.size()));
        } while (node instanceof TerminalNode);
        mutateNode(node);

        mutated.updateSizes();
        mutated.updateSubTreeDepths();
        return mutated;
    }

    private void mutateNode(SymbolicNode node) {
        if (node instanceof FunctionNode) {
            SymbolicNode child;
            if (node.getDepth() >= maxDepth-1 || nodeCount >= maxNodes-1) {
                child = NodeSupplier.getTerminalNode();
            } else {
                child = NodeSupplier.getNode();
            }
            nodeCount ++;

            ((FunctionNode) node).setChild(child);
            mutateNode(child);
        } else if (node instanceof BiFunctionNode) {
            SymbolicNode left;
            SymbolicNode right;
            if (node.getDepth() >= maxDepth-2 || nodeCount >= maxNodes-2) {
                left = NodeSupplier.getTerminalNode();
                right = NodeSupplier.getTerminalNode();
            } else {
                left = NodeSupplier.getNode();
                right = NodeSupplier.getNode();
            }
            nodeCount += 2;

            ((BiFunctionNode) node).setChildren(left, right);
            mutateNode(left);
            mutateNode(right);
        }
    }
}
