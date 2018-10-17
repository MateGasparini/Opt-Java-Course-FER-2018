package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Algorithm 2 from the homework.</p>
 * <p>It rarely performs good even for easy problems.</p>
 * <p>Its weakness is that it stops after finding a local optimum.</p>
 *
 * @author Mate Gašparini
 */
public class GreedyHillClimbingAlgorithm extends EvolutionaryAlgorithm {

    @Override
    public BitVector solve(SATFormula formula) {
        SATFormulaStats stats = new SATFormulaStats(formula);

        BitVector vector = new BitVector(random, formula.getNumberOfVariables());
        stats.setAssignment(vector, false);
        if (stats.isSatisfied()) {
            return vector;
        }

        int bestFitness = stats.getNumberOfSatisfied();
        List<BitVector> bestNeighbors = new ArrayList<>();

        for (int i = 0; i < MAX_ITERATION; i ++) {
            BitVectorNGenerator generator = new BitVectorNGenerator(vector);
            for (BitVector neighbor : generator) {
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
                    if (!bestNeighbors.isEmpty()) {
                        bestNeighbors.add(neighbor);
                    }
                }
            }

            if (bestNeighbors.isEmpty()) break;
            vector = bestNeighbors.get(random.nextInt(bestNeighbors.size()));
            bestNeighbors.clear();
        }

        return null;
    }
}
