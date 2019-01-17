package hr.fer.zemris.optjava.dz9.algorithm.crossover;

import hr.fer.zemris.optjava.dz9.algorithm.solution.SymbolicTreeSolution;
import hr.fer.zemris.optjava.dz9.nodes.SymbolicNode;
import hr.fer.zemris.optjava.dz9.nodes.bifunction.BiFunctionNode;
import hr.fer.zemris.optjava.dz9.nodes.function.FunctionNode;
import hr.fer.zemris.optjava.dz9.nodes.terminal.TerminalNode;

import java.util.List;
import java.util.Random;

/**
 * Crossover which replaces a random subtree from the first parent with a random
 * subtree from the second parent.
 *
 * @author Mate Gašparini
 */
public class SubTreeCrossover implements Crossover<SymbolicTreeSolution> {

    /** Single {@link Random} instance. */
    private Random random = new Random();

    /** Maximum allowed tree depth. */
    private int maxDepth;

    /** Maximum allowed tree node count. */
    private int maxNodes;

    /**
     * Constructor specifying the maximum depth and maximum node count.
     *
     * @param maxDepth The maximum allowed tree depth.
     * @param maxNodes The maximum allowed tree node count.
     */
    public SubTreeCrossover(int maxDepth, int maxNodes) {
        this.maxDepth = maxDepth;
        this.maxNodes = maxNodes;
    }

    @Override
    public SymbolicTreeSolution cross(SymbolicTreeSolution first,
            SymbolicTreeSolution second) {
        SymbolicTreeSolution firstCopy = first.copy();
        List<SymbolicNode> firstList = firstCopy.getTreeList();
        SymbolicNode firstNode;
        do {
            firstNode = firstList.get(random.nextInt(firstList.size()));
        } while (firstNode instanceof TerminalNode);

        SymbolicTreeSolution secondCopy = second.copy();
        List<SymbolicNode> secondList = secondCopy.getTreeList();
        SymbolicNode secondNode = secondList.get(random.nextInt(secondList.size()));

        if (firstNode.getDepth() + secondNode.getSubTreeDepth() < maxDepth
                || first.size() - firstNode.getSize() + secondNode.getSize() < maxNodes) {
            if (firstNode instanceof FunctionNode) {
                ((FunctionNode) firstNode).setChild(secondNode);
            } else if (firstNode instanceof BiFunctionNode) {
                BiFunctionNode biFunctionNode = (BiFunctionNode) firstNode;
                SymbolicNode[] children = biFunctionNode.getChildren();
                if (random.nextDouble() < 0.5) {
                    biFunctionNode.setChildren(secondNode, children[1]);
                } else {
                    biFunctionNode.setChildren(children[0], secondNode);
                }
            }
        } else {
            return random.nextDouble() < 0.5 ? firstCopy : secondCopy;
        }

        firstCopy.updateSizes();
        firstCopy.updateDepths();
        firstCopy.updateSubTreeDepths();
        return firstCopy;
    }
}
