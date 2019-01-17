package hr.fer.zemris.optjava.dz9.algorithm.solution;

import hr.fer.zemris.optjava.dz9.nodes.SymbolicNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Solution which is represented by a {@link SymbolicNode} tree.
 *
 * @author Mate Gašparini
 */
public class SymbolicTreeSolution extends Solution {

    /** Root of the tree. */
    private SymbolicNode root;

    /**
     * Constructor specifying the root of the tree.
     *
     * @param root The specified tree root.
     */
    public SymbolicTreeSolution(SymbolicNode root) {
        this.root = root;
    }

    /**
     * Returns the root of the tree.
     *
     * @return The tree root.
     */
    public SymbolicNode getRoot() {
        return root;
    }

    /**
     * Returns a new solution with a copy of the tree root.
     *
     * @return The copy of this object.
     */
    public SymbolicTreeSolution copy() {
        return new SymbolicTreeSolution(root.copy());
    }

    /**
     * Returns all tree nodes as a {@code List} (filled recursively).
     *
     * @return The constructed tree node {@code List}.
     */
    public List<SymbolicNode> getTreeList() {
        List<SymbolicNode> treeList = new ArrayList<>();
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
