package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Algorithm 5 from the homework.</p>
 * <p>It performs great for easy and good for hard problems.</p>
 * <p>Its strength is that its local search is not deterministically greedy.</p>
 *
 * @author Mate Gašparini
 */
public class RandomWalkSATAlgorithm extends EvolutionaryAlgorithm {

    /** Max number of flips before resetting to a new randomized solution. */
    private static final int MAX_FLIPS = 100;

    /** Probability of mutation (constant p, explained in the homework). */
    private static final double MUTATION_CONSTANT = 0.4;

    @Override
    public BitVector solve(SATFormula formula) {
        List<Clause> unsatisfied = new ArrayList<>();

        for (int i = 0; i < MAX_ITERATION; i ++) {
            MutableBitVector vector =
                    new BitVector(random, formula.getNumberOfVariables()).copy();

            for (int flip = 0; flip < MAX_FLIPS; flip ++) {
                int numberOfClauses = formula.getNumberOfClauses();
                int numberOfSatisfied = 0;
                for (int clauseIndex = 0; clauseIndex < numberOfClauses; clauseIndex ++) {
                    Clause clause = formula.getClause(clauseIndex);
                    if (clause.isSatisfied(vector)) {
                        numberOfSatisfied ++;
                    } else {
                        unsatisfied.add(clause);
                    }
                }

                if (numberOfSatisfied == numberOfClauses) {
                    return vector;
                }

                Clause clause = unsatisfied.get(random.nextInt(unsatisfied.size()));
                alterVector(vector, clause, formula);
                unsatisfied.clear();
            }
        }

        return null;
    }

    /**
     * <p>Mutates the given assignment vector in two ways.</p>
     * <p>With the probability of {@code MUTATION_CONSTANT}, it flips some
     * randomly chosen variable from the given clause in the given vector.</p>
     * <p>With the probability of {@code 1-MUTATION_CONSTANT}, it flips the
     * variable from the given clause in the given vector that results in the
     * highest number of satisfied clauses in the given formula.</p>
     *
     * @param vector The given vector.
     * @param clause The given clause.
     * @param formula The given formula.
     */
    private void alterVector(MutableBitVector vector, Clause clause, SATFormula formula) {
        if (random.nextDouble() < MUTATION_CONSTANT) {
            int literalIndex = random.nextInt(clause.getSize());
            int bitIndex = Math.abs(clause.getLiteral(literalIndex)) - 1;
            vector.set(bitIndex, !vector.get(bitIndex));
        }

        if (random.nextDouble() < 1.0-MUTATION_CONSTANT) {
            int bestIndex = 0;
            int bestScore = -1;
            SATFormulaStats stats = new SATFormulaStats(formula);
            for (int i = 0, size = clause.getSize(); i < size; i ++) {
                int bitIndex = Math.abs(clause.getLiteral(i)) - 1;
                MutableBitVector candidate = vector.copy();
                candidate.set(bitIndex, candidate.get(bitIndex));
                stats.setAssignment(candidate, false);
                int score = stats.getNumberOfSatisfied();
                if (score > bestScore) {
                    bestScore = score;
                    bestIndex = bitIndex;
                }
            }
            vector.set(bestIndex, !vector.get(bestIndex));
        }
    }
}
