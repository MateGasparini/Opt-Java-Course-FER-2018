package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.*;

import java.util.*;

/**
 * <p>Algorithm 6 from the homework.</p>
 * <p>It performs good for easy and bad for hard problems.</p>
 * <p>Its weakness is that it often does not manage to get out of the local
 * optima.</p>
 *
 * @author Mate Gašparini
 */
public class IteratedLocalSearchAlgorithm extends EvolutionaryAlgorithm {

    /** Part of the vector to perturb when stuck in a local optimum. */
    private static final double MUTATION_CONSTANT = 0.4;

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

            if (bestNeighbors.isEmpty()) {
                vector = perturb(vector);
            } else {
                vector = bestNeighbors.get(random.nextInt(bestNeighbors.size()));
                bestNeighbors.clear();
            }
        }

        return null;
    }

    /**
     * Flips a part ({@code MUTATION_CONSTANT}) of the given vector's variables.
     *
     * @param vector The given vector.
     * @return The perturbed vector.
     */
    private BitVector perturb(BitVector vector) {
        MutableBitVector perturbed = vector.copy();
        int size = vector.getSize();
        int mutationCount = (int) (MUTATION_CONSTANT * size);

        Set<Integer> indexes = new HashSet<>();
        int count = 0;
        while (count < mutationCount) {
            int index = random.nextInt(size);
            if (indexes.add(index)) {
                count ++;
            }
        }

        for (int index : indexes) {
            perturbed.set(index, !perturbed.get(index));
        }

        return perturbed;
    }
}
