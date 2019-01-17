package hr.fer.zemris.optjava.dz13.nodes.program;

import hr.fer.zemris.optjava.dz13.nodes.Node;

public abstract class Complex3Node extends ComplexNode {

    public Complex3Node() {
        children = new Node[3];
    }

    public void setChildren(Node first, Node second, Node third) {
        children[0] = first;
        first.setDepth(getDepth() + 1);
        children[1] = second;
        second.setDepth(getDepth() + 1);
        children[2] = third;
        third.setDepth(getDepth() + 1);
    }

    @Override
    public Node copy() {
        try {
            Complex3Node copy = getClass().getConstructor().newInstance();
            copy.children[0] = this.children[0].copy();
            copy.children[1] = this.children[1].copy();
            copy.children[2] = this.children[2].copy();
            copy.setDepth(getDepth());
            copy.setSize(getSize());
            copy.setSubTreeDepth(getSubTreeDepth());
            return copy;
        } catch (Exception ex) {
            return null;
        }
    }
}
