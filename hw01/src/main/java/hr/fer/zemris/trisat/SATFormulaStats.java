package hr.fer.zemris.trisat;

import java.util.Objects;

/**
 * Class which calculates and stores information about some given assignment
 * applied to some specified {@link SATFormula}.
 *
 * @author Mate Gašparini
 */
public class SATFormulaStats {

    /** Used for individual clause percentages when satisfied. */
    private static final double PERCENTAGE_CONSTANT_UP = 0.01;

    /** Used for individual clause percentages when not satisfied. */
    private static final double PERCENTAGE_CONSTANT_DOWN = 0.1;

    /** Used for clause corrections. */
    private static final double PERCENTAGE_UNIT_AMOUNT = 50.0;

    /** The specified {@link SATFormula}. */
    private SATFormula formula;

    /** The number of satisfied clauses. */
    private int numberOfSatisfied;

    /** {@code true} only if all clauses are satisfied. */
    private boolean satisfied;

    /** Array of estimated clause satisfaction percentages. */
    private double[] percentages;

    /** The sum of clause corrections. */
    private double percentageBonus;

    /**
     * Constructor specifying the {@link SATFormula}.
     *
     * @param formula The specified {@link SATFormula}.
     * @throws NullPointerException If the specified {@link SATFormula} is
     *         {@code null}.
     */
    public SATFormulaStats(SATFormula formula) {
        this.formula = Objects.requireNonNull(formula, "Formula cannot be null.");
        this.percentages = new double[formula.getNumberOfClauses()];
    }

    /**
     * Applies the given assignment vector to the {@link SATFormula} and updates
     * all needed information.
     *
     * @param assignment The given assignment vector.
     * @param updatePercentages If {@code true}, clause percentage values will
     *        be updated.
     */
    public void setAssignment(BitVector assignment, boolean updatePercentages) {
        resetValues();
        for (int i = 0, size = formula.getNumberOfClauses(); i < size; i ++) {
            Clause clause = formula.getClause(i);
            if (clause.isSatisfied(assignment)) {
                numberOfSatisfied ++;
                percentageBonus += PERCENTAGE_UNIT_AMOUNT * (1.0 - percentages[i]);
                if (updatePercentages) {
                    percentages[i] += (1.0 - percentages[i]) * PERCENTAGE_CONSTANT_UP;
                }
            } else {
                percentageBonus -= PERCENTAGE_UNIT_AMOUNT * (1.0 - percentages[i]);
                if (updatePercentages) {
                    percentages[i] -= percentages[i] * PERCENTAGE_CONSTANT_DOWN;
                }
            }
        }
        satisfied = numberOfSatisfied == formula.getNumberOfClauses();
    }

    /**
     * Returns the number of satisfied clauses.
     *
     * @return The number of satisfied clauses.
     */
    public int getNumberOfSatisfied() {
        return numberOfSatisfied;
    }

    /**
     * Returns {@code true} if the {@link SATFormula} is satisfied.
     *
     * @return {@code true} if the {@link SATFormula} is satisfied, or
     *         {@code false} otherwise.
     */
    public boolean isSatisfied() {
        return satisfied;
    }

    /**
     * Returns the sum of clause corrections.
     *
     * @return The sum of clause corrections.
     */
    public double getPercentageBonus() {
        return percentageBonus;
    }

    /**
     * Returns the estimate of the satisfaction percentage of the clause
     * specified by the given index.
     *
     * @param index The given index.
     * @return The corresponding estimate percentage value.
     */
    public double getPercentage(int index) {
        return percentages[index];
    }

    /**
     * Resets all information which needs to be reset before applying a new
     * assignment vector to the {@link SATFormula}.
     */
    private void resetValues() {
        numberOfSatisfied = 0;
        satisfied = false;
        percentageBonus = 0.0;
    }
}
