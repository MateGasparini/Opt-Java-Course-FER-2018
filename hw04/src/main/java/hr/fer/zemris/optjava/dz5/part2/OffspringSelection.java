package hr.fer.zemris.optjava.dz5.part2;

import hr.fer.zemris.optjava.dz5.part2.crossover.OrderCrossover;
import hr.fer.zemris.optjava.dz5.part2.function.ExpenseFunction;
import hr.fer.zemris.optjava.dz5.part2.mutator.SwitchMutator;
import hr.fer.zemris.optjava.dz5.part2.selection.NTournamentSelection;
import hr.fer.zemris.optjava.dz5.part2.solution.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * <i>Offspring selection</i> (OS) genetic algorithm implementation.
 */
public class OffspringSelection {

    /** Maximum number of iterations.*/
    private static final int MAX_ITERATION = 10;

    /** Preferred ratio of number of successful and total children. */
    private static final double SUCCESS_RATIO = 0.3;

    /** Maximum selection pressure value. */
    private static final double MAX_SELECTION_PRESSURE = 100;

    /** Lower bound for the comparison factor value. */
    private static final double LOWER_BOUND = 0.3;

    /** Upper bound for the comparison factor value. */
    private static final double UPPER_BOUND = 1.0;

    /** Single {@link Random} instance. */
    private Random random = new Random();

    /** The specified function which needs to be minimized. */
    private ExpenseFunction function;

    /** Parent selection strategy. */
    private NTournamentSelection selection = new NTournamentSelection(5);

    /** Parent crossover operator. */
    private OrderCrossover crossover = new OrderCrossover();

    /** Child mutation strategy. */
    private SwitchMutator mutator = new SwitchMutator();

    /**
     * Constructor specifying the function which needs to be minimized.
     *
     * @param function The specified function.
     */
    public OffspringSelection(ExpenseFunction function) {
        this.function = function;
    }

    /**
     * Runs the algorithm and generates some offspring after reaching maximum
     * number of iterations or maximum selection pressure.
     *
     * @param parents The given (sub)population's parents.
     * @return The generated offspring.
     */
    public List<Solution> generateOffspring(List<Solution> parents) {
        int size = parents.size();
        double comparisonFactor = LOWER_BOUND;
        double comparisonFactorStep = (UPPER_BOUND-LOWER_BOUND) / MAX_ITERATION;
        double actualSelectionPressure = 1.0;

        for (int i = 0; i < MAX_ITERATION; i ++) {
            if (actualSelectionPressure > MAX_SELECTION_PRESSURE) break;
            comparisonFactor += comparisonFactorStep;

            List<Solution> children = new ArrayList<>();
            List<Solution> pool = new ArrayList<>();

            while (children.size() < size*SUCCESS_RATIO
                    && (children.size() + pool.size()) < size*MAX_SELECTION_PRESSURE) {
                Solution first = selection.getSolution(parents);
                Solution second = selection.getSolution(parents);

                Solution child = crossover.cross(first, second);
                mutator.mutate(child);

                child.value = value(child);
                child.fitness = fitness(child);

                if (child.fitness < second.fitness + (first.fitness-second.fitness)*comparisonFactor) {
                    pool.add(child);
                } else {
                    children.add(child);
                }
            }

            actualSelectionPressure = (double) (children.size() + pool.size()) / size;

            while (children.size() < size) {
                if (pool.size() != 0) {
                    children.add(pool.get(random.nextInt(pool.size())));
                } else {
                    Solution first = parents.get(random.nextInt(parents.size()));
                    Solution second = parents.get(random.nextInt(parents.size()));

                    Solution child = crossover.cross(first, second);
                    child.value = value(child);
                    child.fitness = fitness(child);
                    children.add(child);
                }
            }
            parents = children;
        }

        return parents;
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
