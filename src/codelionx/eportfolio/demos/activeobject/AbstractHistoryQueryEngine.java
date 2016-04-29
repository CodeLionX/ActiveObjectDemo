package codelionx.eportfolio.demos.activeobject;


/**
 * Abstract History Query Engine - some engine that performs synchronous queries which take some time. A limitation
 * (provider contract) shall be that only a maximum number of parallel requests are allowed to reduce load on the
 * assumed backend system.
 */
public abstract class AbstractHistoryQueryEngine {

    /**
     * Perform a synchronous query, which may take some time.
     * @param firstName person's first name
     * @param lastName person's last name
     * @return data list of (String[4]=[firstName, lastName, birthday, data]) according to the query or empty list if
     *         not found
     */
    public abstract String[] queryHistoryData(String firstName, String lastName);

}
