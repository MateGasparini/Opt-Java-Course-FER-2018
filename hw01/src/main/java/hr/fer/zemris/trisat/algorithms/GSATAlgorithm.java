package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Algorithm 4 from the homework.</p>
 * <p>It performs great for both easy and hard problems.</p>
 * <p>Its strength is that, after not being able to escape a local optimum, it
 * restarts the search with a random solution.</p>
 *
 * @author Mate Gašparini
 */
public class GSATAlgorithm extends EvolutionaryAlgorithm {

    /** Max number of flips before resetting to a new randomized solution. */
    private static final int MAX_FLIPS = 100;

    @Override
    public BitVector solve(SATFormula formula) {
        SATFormulaStats stats = new SATFormulaStats(formula);

        List<BitVector> bestNeighbors = new ArrayList<>();

        for (int i = 0; i < MAX_ITERATION; i ++) {
            BitVector vector = new BitVector(random, formula.getNumberOfVariables());
            stats.setAssignment(vector, false);
            if (stats.isSatisfied()) {
                return vector;
            }

            for (int flip = 0; flip < MAX_FLIPS; flip ++) {
                int bestFitness = 0;
                for (BitVector neighbor : new BitVectorNGenerator(vector)) {
                    stats.setAssignment(neighbor, false);
                    if (stats.isSatisfied()) {
                        return neighbor;
                    }

                    int fitness = stats.getNumberOfSatisfied();
                    if (fitness > bestFitness) {
                        bestFitness = fitness;
                        bestNeighbors.clear();
                        bestNeighbors.add(neighbor);
                    } else if (fitness == bestFitness) {
                        bestNeighbors.add(neighbor);
                    }
                }

                vector = bestNeighbors.get(random.nextInt(bestNeighbors.size()));
                bestNeighbors.clear();
            }
        }

        return null;
    }
}
