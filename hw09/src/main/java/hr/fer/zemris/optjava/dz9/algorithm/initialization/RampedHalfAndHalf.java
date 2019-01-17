package hr.fer.zemris.optjava.dz9.algorithm.initialization;

import hr.fer.zemris.optjava.dz9.algorithm.solution.SymbolicTreeSolution;
import hr.fer.zemris.optjava.dz9.nodes.NodeSupplier;
import hr.fer.zemris.optjava.dz9.nodes.SymbolicNode;
import hr.fer.zemris.optjava.dz9.nodes.bifunction.BiFunctionNode;
import hr.fer.zemris.optjava.dz9.nodes.function.FunctionNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Initialization which, for each specified depth, creates half of the tree
 * solutions using the <i>grow</i>, and the other half using the <i>full</i>
 * method.
 *
 * @author Mate Gašparini
 */
public class RampedHalfAndHalf implements Initialization<SymbolicTreeSolution> {

    /** Minimum allowed tree depth. */
    private static final int MIN_DEPTH = 2;

    /** Maximum allowed tree depth. */
    private int maxDepth;

    /** Maximum allowed tree node count. */
    private int maxNodes;

    /**
     * Constructor specifying the maximum tree depth and maximum node count.
     *
     * @param maxDepth The maximum allowed tree depth.
     * @param maxNodes The maximum allowed tree node count.
     */
    public RampedHalfAndHalf(int maxDepth, int maxNodes) {
        this.maxDepth = maxDepth;
        this.maxNodes = maxNodes;
    }

    @Override
    public List<SymbolicTreeSolution> getPopulation(int populationSize) {
        List<SymbolicTreeSolution> population = new ArrayList<>(populationSize);
        int solutionsPerDepth = populationSize - MIN_DEPTH + 1;
        int fullSolutionsPerDepth = solutionsPerDepth / 2;
        int growSolutionsPerDepth = populationSize - fullSolutionsPerDepth;

        for (int depth = MIN_DEPTH; depth <= maxDepth; depth ++) {
            for (int i = 0; i < fullSolutionsPerDepth; i ++) {
                population.add(getFullTree(depth - 1));
            }

            for (int i = 0; i < growSolutionsPerDepth; i ++) {
                population.add(getGrowTree(depth - 1));
            }
        }

        return population;
    }

    private SymbolicTreeSolution getFullTree(int depth) {
        SymbolicNode root = NodeSupplier.getNonTerminalNode();
        SymbolicTreeSolution solution = new SymbolicTreeSolution(root);
        buildFull(root, depth - 1);

        solution.updateSizes();
        solution.updateSubTreeDepths();
        return solution;
    }

    private void buildFull(SymbolicNode parent, int remaining) {
        if (parent instanceof FunctionNode) {
            SymbolicNode child = fullChild(remaining);
            ((FunctionNode) parent).setChild(child);
            buildFull(child, remaining - 1);
        } else if (parent instanceof BiFunctionNode) {
            SymbolicNode left = fullChild(remaining);
            SymbolicNode right = fullChild(remaining);
            ((BiFunctionNode) parent).setChildren(left, right);
            buildFull(left, remaining - 1);
            buildFull(right, remaining - 1);
        }
    }

    private SymbolicNode fullChild(int remaining) {
        return remaining != 0 ?
                NodeSupplier.getNonTerminalNode() : NodeSupplier.getTerminalNode();
    }

    private SymbolicTreeSolution getGrowTree(int depth) {
        SymbolicNode root = NodeSupplier.getNonTerminalNode();
        SymbolicTreeSolution solution = new SymbolicTreeSolution(root);
        buildGrow(root, depth - 1);

        solution.updateSizes();
        solution.updateSubTreeDepths();
        return solution;
    }

    private void buildGrow(SymbolicNode parent, int remaining) {
        if (parent instanceof FunctionNode) {
            SymbolicNode child = growChild(remaining);
            ((FunctionNode) parent).setChild(child);
            buildGrow(child, remaining - 1);
        } else if (parent instanceof BiFunctionNode) {
            SymbolicNode left = growChild(remaining);
            SymbolicNode right = growChild(remaining);
            ((BiFunctionNode) parent).setChildren(left, right);
            buildGrow(left, remaining - 1);
            buildGrow(right, remaining - 1);
        }
    }

    private SymbolicNode growChild(int remaining) {
        return remaining != 0 ?
                NodeSupplier.getNode() : NodeSupplier.getTerminalNode();
    }
}
