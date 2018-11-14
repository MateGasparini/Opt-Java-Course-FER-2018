package hr.fer.zemris.optjava.dz5.part2;

import hr.fer.zemris.optjava.dz5.part2.function.ExpenseFunction;
import hr.fer.zemris.optjava.dz5.part2.solution.Population;
import hr.fer.zemris.optjava.dz5.part2.solution.Solution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <i>Self-adaptive Segregative Genetic Algorithm with Simulated Annealing
 * Aspects</i> (SASEGASA) implementation.
 *
 * @author Mate Gašparini
 */
public class SASEGASA {

    /** Genetic algorithm used for subpopulation evolution. */
    private OffspringSelection offspringSelection;

    /** Function which needs to be minimized. */
    private ExpenseFunction function;

    /** Size of the whole population. */
    private int populationSize;

    /** Number of subpopulations. */
    private int subPopulationCount;

    /** Current size of the subpopulation (excluding the last subpopulation). */
    private int subPopulationSize;

    /**
     * Constructor specifying all algorithm parameters.
     *
     * @param function The specified function which needs to be minimized.
     * @param populationSize The specified size of the whole population.
     * @param subPopulationCount The specified number of subpopulations.
     */
    public SASEGASA(ExpenseFunction function, int populationSize, int subPopulationCount) {
        this.offspringSelection = new OffspringSelection(function);
        this.function = function;
        this.populationSize = populationSize;
        this.subPopulationCount = subPopulationCount;
        this.subPopulationSize = populationSize / subPopulationCount;
    }

    /**
     * Runs the algorithm until there is a single (sub)population left and its
     * offspring has been generated.
     *
     * @return The best solution from the final (sub)population.
     */
    public Solution run() {
        List<Population> populations = generateSubPopulations();

        while (true) {
            List<Population> generated = new ArrayList<>();
            for (int i = 0; i < populations.size(); i ++) {
                generated.add(new Population(
                        offspringSelection.generateOffspring(populations.get(i).getSolutions())
                ));
            }
            populations = generated;

            if (subPopulationCount == 1) break;

            subPopulationCount --;
            subPopulationSize = populationSize / subPopulationCount;
            joinSubPopulations(populations);
        }

        List<Solution> finalSolutions = populations.get(0).getSolutions();
        finalSolutions.sort(Comparator.reverseOrder());
        return finalSolutions.get(0);
    }

    /**
     * Generates the starting subpopulations and returns them in a {@code List}.
     *
     * @return The generated starting subpopulations.
     */
    private List<Population> generateSubPopulations() {
        List<Population> populations = new ArrayList<>(subPopulationCount);

        int currentSize = 0;
        for (int i = 0; i < subPopulationCount; i ++) {
            int currentSubSize = i != subPopulationCount - 1 ?
                    subPopulationSize : populationSize - currentSize;

            Population subPopulation = new Population(currentSubSize, function.getSolutionSize());
            for (Solution solution : subPopulation.getSolutions()) {
                solution.value = value(solution);
                solution.fitness = fitness(solution);
            }
            populations.add(subPopulation);

            currentSize += currentSubSize;
        }
        return populations;
    }

    /**
     * Joins the given subpopulations using the current subpopulation count
     * value.
     *
     * @param populations The given subpopulations.
     */
    private void joinSubPopulations(List<Population> populations) {
        List<Solution> allSolutions = new ArrayList<>(populationSize);
        for (int i = 0; i < subPopulationCount+1; i ++) {
            allSolutions.addAll(populations.get(i).getSolutions());
        }
        populations.clear();

        int currentSize = 0;
        for (int i = 0; i < subPopulationCount; i ++) {
            int currentSubSize = i != subPopulationCount - 1 ?
                    subPopulationSize : populationSize - currentSize;

            List<Solution> subSolutions = new ArrayList<>(currentSubSize);
            for (int j = 0; j < currentSubSize; j ++) {
                subSolutions.add(allSolutions.get(currentSize + j));
            }
            populations.add(new Population(subSolutions));

            currentSize += currentSubSize;
        }
    }

    /**
     * Returns the calculated value of the function for the given solution.
     *
     * @param solution The given solution.
     * @return The calculated function value.
     */
    private double value(Solution solution) {
        return function.getExpense(solution.values);
    }

    /**
     * Returns the fitness of the given solution.
     *
     * @param solution The given solution.
     * @return The calculated fitness value.
     */
    private double fitness(Solution solution) {
        return 1.0/solution.value;
    }
}
