package hr.fer.zemris.optjava.dz9.algorithm.solution;

import hr.fer.zemris.optjava.dz9.algorithm.dataset.Dataset;
import hr.fer.zemris.optjava.dz9.nodes.NodeSupplier;
import hr.fer.zemris.optjava.dz9.nodes.SymbolicNode;
import hr.fer.zemris.optjava.dz9.nodes.bifunction.BiFunctionNode;
import hr.fer.zemris.optjava.dz9.nodes.function.FunctionNode;
import hr.fer.zemris.optjava.dz9.nodes.terminal.VariableNode;

import java.util.List;

/**
 * Class which is used to evaluate a population of solutions using the specified
 * symbolic regression dataset.
 *
 * @author Mate Gašparini
 */
public class SymbolicEvaluator {

    /** The specified dataset. */
    private Dataset dataset;

    /** Dataset sample counter. */
    private int sampleIndex; // To minimize stack allocation for recursion.

    /**
     * Constructor specifying the acquired function dataset.
     *
     * @param dataset The specified dataset.
     */
    public SymbolicEvaluator(Dataset dataset) {
        this.dataset = dataset;
    }

    /**
     * Calculates and sets the value and fitness for each solution in the given
     * population.
     *
     * @param population The given population.
     */
    public void evaluatePopulation(List<SymbolicTreeSolution> population) {
        population.forEach(solution -> {
            double error = dataset.calculateError(calculateOutputs(solution));
            solution.setValue(error);
            solution.setFitness(fitness(error));
        });
    }

    private double[] calculateOutputs(SymbolicTreeSolution solution) {
        double[] outputs = new double[dataset.getSampleCount()];
        for (sampleIndex = 0; sampleIndex < outputs.length; sampleIndex ++) {
            prepareVariables(solution.getRoot());
            outputs[sampleIndex] = solution.getRoot().calculateValue();
        }
        return outputs;
    }

    private void prepareVariables(SymbolicNode node) {
        if (node instanceof BiFunctionNode) {
            BiFunctionNode biFunctionNode = (BiFunctionNode) node;
            SymbolicNode[] children = biFunctionNode.getChildren();
            prepareVariables(children[0]);
            prepareVariables(children[1]);
        } else if (node instanceof FunctionNode) {
            prepareVariables(((FunctionNode) node).getChild());
        } else if (node instanceof VariableNode) {
            VariableNode variableNode = (VariableNode) node;
            variableNode.setValue(dataset.getInput(
                    sampleIndex, NodeSupplier.getVariableIndex(variableNode.getId())));
        }
    }

    private double fitness(double error) {
        return 1.0 / error;
    }
}
