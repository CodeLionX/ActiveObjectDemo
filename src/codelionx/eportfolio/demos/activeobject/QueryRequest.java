package codelionx.eportfolio.demos.activeobject;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class QueryRequest extends FutureTask<String[]> {

    private final String paramFirstName;
    private final String paramLastName;

    /**
     * Creates an objectified request
     * @param engine query engine to be used
     * @param paramFirstName person's first name to query for
     * @param paramLastName person's last name to query for
     */
    public QueryRequest(final DatabaseServant engine, final String paramFirstName,
            final String paramLastName) {
        super(new Callable<String[]>() {

            @Override
            public String[] call() throws Exception {
                return engine.queryData(paramFirstName, paramLastName);
            }

        });
        this.paramFirstName = paramFirstName;
        this.paramLastName = paramLastName;
        System.out.println(this.toString() + " created");
    }

    public String getParamFirstName() {
        return paramFirstName;
    }

    public String getParamLastName() {
        return paramLastName;
    }

    @Override
    public void run() {
        System.out.println(this.getClass().getSimpleName() + ".run() called for " + this.toString());
        super.run();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "({paramFirstName=" + paramFirstName + ", paramLastName="
                + paramLastName + "})";
    }

}
