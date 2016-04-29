package codelionx.eportfolio.demos.activeobject;


public class QueryRequestFuture {

    private final QueryRequest queryRequest;

    /**
     * Creates a new Future based on the request data
     * @param queryRequest objectified request which has to be observed
     */
    public QueryRequestFuture(QueryRequest queryRequest) {
        this.queryRequest = queryRequest;
    }

    /**
     * Returns the result if - and only if computation is done, otherwise null (non-blocking)
     * @return result or null (if not completed or cancelled)
     */
    public String[] getResult() {
        System.out.println(this.getClass().getSimpleName() + ".getResult() called");
        try {
            if (queryRequest.isDone() && !queryRequest.isCancelled()) {
                return queryRequest.get();
            }
        }
        catch (Exception ex) {
            System.err.println("Failed to get result!");
        }
        return null;
    }

    /**
     * Cancels the query
     */
    public void cancelQuery() {
        System.out.println(this.getClass().getSimpleName() + ".cancelQuery() called");
        queryRequest.cancel(true);
    }

    /**
     * Determines whether the query is cancelled
     * @return true if this query is cancelled
     */
    public boolean isQueryCancelled() {
        boolean res = queryRequest.isCancelled();
        System.out.println(this.getClass().getSimpleName() + ".isQueryCancelled() called - " + res);
        return res;
    }

    /**
     * Determines whether the query has finished
     * @return true if this query is done
     */
    public boolean isQueryDone() {
        boolean res = queryRequest.isDone();
        System.out.println(this.getClass().getSimpleName() + ".isQueryDone() called - " + res);
        return res;
    }

}
