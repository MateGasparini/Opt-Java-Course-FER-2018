package hr.fer.zemris.optjava.dz13.algorithm.initialization;

import hr.fer.zemris.optjava.dz13.algorithm.solution.TreeSolution;
import hr.fer.zemris.optjava.dz13.nodes.Node;
import hr.fer.zemris.optjava.dz13.nodes.NodeSupplier;
import hr.fer.zemris.optjava.dz13.nodes.program.Complex2Node;
import hr.fer.zemris.optjava.dz13.nodes.program.Complex3Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Initialization which, for each specified depth, creates half of the tree
 * solutions using the <i>grow</i>, and the other half using the <i>full</i>
 * method.
 *
 * @author Mate Gašparini
 */
public class RampedHalfAndHalf implements Initialization<TreeSolution> {

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
    public List<TreeSolution> getPopulation(int populationSize) {
        List<TreeSolution> population = new ArrayList<>(populationSize);
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

    private TreeSolution getFullTree(int depth) {
        Node root = NodeSupplier.getNonTerminalNode();
        TreeSolution solution = new TreeSolution(root);
        buildFull(root, depth - 1);

        solution.updateSizes();
        solution.updateSubTreeDepths();
        return solution;
    }

    private void buildFull(Node parent, int remaining) {
        if (parent instanceof Complex2Node) {
            Node first = fullChild(remaining);
            Node second = fullChild(remaining);
            ((Complex2Node) parent).setChildren(first, second);
            buildFull(first, remaining - 1);
            buildFull(second, remaining - 1);
        } else if (parent instanceof Complex3Node) {
            Node first = fullChild(remaining);
            Node second = fullChild(remaining);
            Node third = fullChild(remaining);
            ((Complex3Node) parent).setChildren(first, second, third);
            buildFull(first, remaining - 1);
            buildFull(second, remaining - 1);
            buildFull(third, remaining - 1);
        }
    }

    private Node fullChild(int remaining) {
        return remaining != 0 ?
                NodeSupplier.getNonTerminalNode() : NodeSupplier.getTerminalNode();
    }

    private TreeSolution getGrowTree(int depth) {
        Node root = NodeSupplier.getNonTerminalNode();
        TreeSolution solution = new TreeSolution(root);
        buildGrow(root, depth - 1);

        solution.updateSizes();
        solution.updateSubTreeDepths();
        return solution;
    }

    private void buildGrow(Node parent, int remaining) {
        if (parent instanceof Complex2Node) {
            Node first = growChild(remaining);
            Node second = growChild(remaining);
            ((Complex2Node) parent).setChildren(first, second);
            buildGrow(first, remaining - 1);
            buildGrow(second, remaining - 1);
        } else if (parent instanceof Complex3Node) {
            Node first = growChild(remaining);
            Node second = growChild(remaining);
            Node third = growChild(remaining);
            ((Complex3Node) parent).setChildren(first, second, third);
            buildGrow(first, remaining - 1);
            buildGrow(second, remaining - 1);
            buildGrow(third, remaining - 1);
        }
    }

    private Node growChild(int remaining) {
        return remaining != 0 ?
                NodeSupplier.getNode() : NodeSupplier.getTerminalNode();
    }
}
