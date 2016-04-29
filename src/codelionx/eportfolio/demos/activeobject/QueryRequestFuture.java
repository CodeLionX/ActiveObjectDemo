package codelionx.eportfolio.demos.activeobject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class QueryRequestFuture implements Future<String[]> {

    private final QueryRequest queryRequest;

    /**
     * Creates a new Future based on the request data
     * @param queryRequest objectified request which has to be observed
     */
    public QueryRequestFuture(QueryRequest queryRequest) {
    	super();
        this.queryRequest = queryRequest;
    }

    public boolean cancel() {
        System.out.println(this.getClass().getSimpleName() + ".cancel() called");
        return queryRequest.cancel(true);
    }

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		System.out.println(this.getClass().getSimpleName() + ".cancel(" + mayInterruptIfRunning + ") called");
		return queryRequest.cancel(mayInterruptIfRunning);
	}

	@Override
	public String[] get() throws InterruptedException, ExecutionException {
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

	@Override
	public String[] get(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException, TimeoutException {
		if(unit != TimeUnit.MILLISECONDS) {
			throw new IllegalArgumentException("only Milliseconds are allowed!");
		}
		long startTime = System.currentTimeMillis();
        while (!isDone()) {
            if (System.currentTimeMillis() >= (startTime + timeout)) {
                cancel();
                break;
            }
            Thread.sleep(500);
        }
        return get();
	}

	@Override
	public boolean isCancelled() {
		boolean res = queryRequest.isCancelled();
        System.out.println(this.getClass().getSimpleName() + ".isQueryCancelled() called - " + res);
        return res;
	}

	@Override
	public boolean isDone() {
		boolean res = queryRequest.isDone();
        System.out.println(this.getClass().getSimpleName() + ".isQueryDone() called - " + res);
        return res;
	}

}
