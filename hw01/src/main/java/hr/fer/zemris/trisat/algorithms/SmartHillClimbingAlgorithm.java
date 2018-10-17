package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

import java.util.*;

/**
 * <p>Algorithm 3 from the homework.</p>
 * <p>It performs great for both easy and hard problems.</p>
 * <p>Its strength is that, through collecting data related to each clause
 * satisfaction frequency, it manages to find a way out of the local optima
 * towards the global optimum.</p>
 *
 * @author Mate Gašparini
 */
public class SmartHillClimbingAlgorithm extends EvolutionaryAlgorithm {

    /** Number of assignments with best fitness from which the next one is chosen. */
    private static final int NUMBER_OF_BEST = 2;

    @Override
    public BitVector solve(SATFormula formula) {
        SATFormulaStats stats = new SATFormulaStats(formula);
        BitVector vector = new BitVector(random, formula.getNumberOfVariables());

        List<AssignmentScore> scores = new ArrayList<>();

        for (int i = 0; i < MAX_ITERATION; i ++) {
            stats.setAssignment(vector, true);

            for (BitVector neighbor : new BitVectorNGenerator(vector)) {
                stats.setAssignment(neighbor, false);
                if (stats.isSatisfied()) {
                    return neighbor;
                }

                double fitness = stats.getNumberOfSatisfied() + stats.getPercentageBonus();
                scores.add(new AssignmentScore(neighbor, fitness));
            }

            scores.sort(Collections.reverseOrder());
            vector = scores.get(random.nextInt(NUMBER_OF_BEST)).assignment;
            scores.clear();
        }

        return null;
    }

    /**
     * Represents a pair of some assignment and its score (fitness value).
     */
    private static class AssignmentScore implements Comparable<AssignmentScore> {

        /** The specified assignment. */
        private BitVector assignment;

        /** The specified score (fitness). */
        private double score;

        /**
         * Constructor specifying the assignment and its score.
         *
         * @param assignment The specified assignment.
         * @param score The specified score (fitness).
         */
        public AssignmentScore(BitVector assignment, double score) {
            this.assignment = assignment;
            this.score = score;
        }

        @Override
        public int compareTo(AssignmentScore other) {
            return Double.compare(this.score, other.score);
        }
    }
}
