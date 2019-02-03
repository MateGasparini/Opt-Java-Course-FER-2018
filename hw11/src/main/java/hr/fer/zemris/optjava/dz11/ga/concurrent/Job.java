package hr.fer.zemris.optjava.dz11.ga.concurrent;

/**
 * Models a single task for a worker thread.
 *
 * @author Mate Gašparini
 */
public class Job {

    /** The task which ends the worker thread's life. */
    public static final Job PILL = new Job();

    /** The specified task. */
    private Runnable task;

    /**
     * Constructor which specifies the task.
     *
     * @param task The specified task.
     */
    public Job(Runnable task) {
        this.task = task;
    }

    /**
     * Default private constructor (for the {@link #PILL} instance).
     */
    private Job() {
    }

    /**
     * Runs the specified task (in the current thread).
     */
    public void runTask() {
        task.run();
    }
}
