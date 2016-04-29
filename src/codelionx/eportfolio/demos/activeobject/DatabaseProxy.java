package codelionx.eportfolio.demos.activeobject;


/**
 * History Query Component - the api from the client's point of view, it provides asynchronous access to the underlying
 * engine to perform queries possibly taking some time.
 */
public class DatabaseProxy {

	/**
     * underlying database/servant (injected by constructor)
     */
    private final AbstractHistoryQueryEngine engine;

    /**
     * Scheduler used to schedule queries to be executed (injected by constructor)
     */
    private final HistoryQueryScheduler scheduler;

    /**
     * Creates a new query leveraging the given engine
     * @param engine engine to be used for querying
     * @param scheduler used to schedule queries to be executed
     */
    public DatabaseProxy(AbstractHistoryQueryEngine engine, HistoryQueryScheduler scheduler) {
        this.engine = engine;
        this.scheduler = scheduler;
    }

    /**
     * Triggers a query according to the given parameters and returns immediately. The returned Future allows the client
     * to poll for the result from time to time.
     * @param firstName person's first name to query for
     * @param lastName person's last name to query for
     * @return Future for communication and result retrieval
     */
    public QueryRequestFuture queryData(String firstName, String lastName) {
        System.out.println(this.getClass().getSimpleName() + ".queryHistoryData('" + firstName + "', '" + lastName + "') called");
        QueryRequest objectifiedRequest = new QueryRequest(engine, firstName, lastName);
        QueryRequestFuture future = new QueryRequestFuture(objectifiedRequest);
        scheduler.schedule(objectifiedRequest);
        return future;
    }

}
