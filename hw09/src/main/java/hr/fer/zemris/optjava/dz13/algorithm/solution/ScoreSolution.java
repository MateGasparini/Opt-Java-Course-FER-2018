package hr.fer.zemris.optjava.dz13.algorithm.solution;

/**
 * Solution to some score-based optimization problem.
 *
 * @author Mate Gašparini
 */
public abstract class ScoreSolution implements Comparable<ScoreSolution> {

    /** The achieved score. */
    private int score;

    /**
     * Returns the achieved score.
     *
     * @return The score value.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score to 0.
     */
    public void resetScore() {
        score = 0;
    }

    /**
     * Increases the score by 1.
     */
    public void incrementScore() {
        score ++;
    }

    @Override
    public int compareTo(ScoreSolution other) {
        return Double.compare(this.score, other.score);
    }
}
