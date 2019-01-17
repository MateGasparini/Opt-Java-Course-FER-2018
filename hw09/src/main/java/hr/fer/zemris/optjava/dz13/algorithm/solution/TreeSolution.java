package hr.fer.zemris.optjava.dz13.algorithm.solution;

import hr.fer.zemris.optjava.dz13.nodes.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Solution which is represented by a {@link Node} tree.
 *
 * @author Mate Gašparini
 */
public class TreeSolution extends ScoreSolution {

    /** Root of the tree. */
    private Node root;

    /**
     * Constructor specifying the root of the tree.
     *
     * @param root The specified tree root.
     */
    public TreeSolution(Node root) {
        this.root = root;
    }

    /**
     * Returns the root of the tree.
     *
     * @return The tree root.
     */
    public Node getRoot() {
        return root;
    }

    /**
     * Returns a new solution with a copy of the tree root.
     *
     * @return The copy of this object.
     */
    public TreeSolution copy() {
        return new TreeSolution(root.copy());
    }

    /**
     * Returns all tree nodes as a {@code List} (filled recursively).
     *
     * @return The constructed tree node {@code List}.
     */
    public List<Node> getTreeList() {
        List<Node> treeList = new ArrayList<>();
        root.fillList(treeList);
        return treeList;
    }

    /**
     * Returns the size of the tree.
     *
     * @return The tree size.
     */
    public int size() {
        return root.getSize();
    }

    /**
     * Updates the size values across the tree.
     */
    public void updateSizes() {
        root.updateSize();
    }

    /**
     * Updates the subtree depth values across the tree.
     */
    public void updateSubTreeDepths() {
        root.updateSubTreeDepth();
    }

    /**
     * Updates the depth values across the tree.
     */
    public void updateDepths() {
        root.updateDepth(0);
    }
}
