package hr.fer.zemris.optjava.dz13.algorithm.crossover;

import hr.fer.zemris.optjava.dz13.algorithm.solution.TreeSolution;
import hr.fer.zemris.optjava.dz13.nodes.Node;
import hr.fer.zemris.optjava.dz13.nodes.action.ActionNode;
import hr.fer.zemris.optjava.dz13.nodes.program.Complex2Node;
import hr.fer.zemris.optjava.dz13.nodes.program.Complex3Node;

import java.util.List;
import java.util.Random;

/**
 * Crossover which replaces a random subtree from the first parent with a random
 * subtree from the second parent.
 *
 * @author Mate Gašparini
 */
public class SubTreeCrossover implements Crossover<TreeSolution> {

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
    public TreeSolution cross(TreeSolution first, TreeSolution second) {
        TreeSolution firstCopy = first.copy();
        List<Node> firstList = firstCopy.getTreeList();
        Node firstNode;
        do {
            firstNode = firstList.get(random.nextInt(firstList.size()));
        } while (firstNode instanceof ActionNode);

        TreeSolution secondCopy = second.copy();
        List<Node> secondList = secondCopy.getTreeList();
        Node secondNode = secondList.get(random.nextInt(secondList.size()));

        if (firstNode.getDepth() + secondNode.getSubTreeDepth() < maxDepth
                || first.size() - firstNode.getSize() + secondNode.getSize() < maxNodes) {
            if (firstNode instanceof Complex2Node) {
                Complex2Node complex2Node = (Complex2Node) firstNode;
                Node[] children = complex2Node.getChildren();

                if (random.nextDouble() < 0.5) {
                    complex2Node.setChildren(secondNode, children[1]);
                } else {
                    complex2Node.setChildren(children[0], secondNode);
                }
            } else if (firstNode instanceof Complex3Node) {
                Complex3Node complex3Node = (Complex3Node) firstNode;
                Node[] children = complex3Node.getChildren();

                double randomValue = random.nextDouble();
                if (randomValue < 0.33) {
                    complex3Node.setChildren(secondNode, children[1], children[2]);
                } else if (randomValue < 0.66) {
                    complex3Node.setChildren(children[0], secondNode, children[2]);
                } else {
                    complex3Node.setChildren(children[0], children[1], secondNode);
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
