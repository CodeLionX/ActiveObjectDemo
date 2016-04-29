package codelionx.eportfolio.demos.activeobject;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class QueryScheduler {

    private final Executor executor;

    /**
     * Creates new scheduler allowing the specified amount of parallel executions
     * @param maxParallelWorkers number of calls that shall execute in parallel
     */
    public QueryScheduler(int maxParallelWorkers) {
        System.out.println(this.getClass().getSimpleName() + " created - working on queue with " + maxParallelWorkers
                + " worker threads in parallel.");
        // the chosen executor internally manages a queue and ensures that
        // only the given maximum of calls will execute in parallel
        // therefore we do not need a ActivationQueue
        this.executor = Executors.newFixedThreadPool(maxParallelWorkers);
    }

    /**
     * schedules the specified task and returns immediately
     * @param task some task to be executed in the future
     */
    public void schedule(QueryRequest task) {
        System.out.println(this.getClass().getSimpleName() + ".schedule() called.");
        System.out.println("enqueuing objectified request: " + task.toString());
        executor.execute(task);
    }

}
