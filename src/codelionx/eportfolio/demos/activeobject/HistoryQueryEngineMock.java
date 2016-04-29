package codelionx.eportfolio.demos.activeobject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HistoryQueryEngineMock extends AbstractHistoryQueryEngine {
    /**
     * Simulated query duration in milliseconds
     */
    private final long queryDurationMillis;
    private final Map<String, String[]> archive = new ConcurrentHashMap<>();

    public HistoryQueryEngineMock(List<String[]> seedData, long queryDurationMillis) {
        this.queryDurationMillis = queryDurationMillis;
        for (String[] data : seedData) {
            put(data[0] + "##" + data[1], data);
        }
    }

    /**
     * helper method for testing, adds the "back end" data related to the key
     * @param key identifier
     * @param data values to put
     */
    private void put(String key, String[] data) {
        String[] content = archive.get(key);
        if (content == null) {
            archive.put(key, data);
        }
    }

    @Override
    public String[] queryHistoryData(String firstName, String lastName) {
        System.out.println(this.getClass().getSimpleName() + ".queryHistoryData('" + firstName + "', '" + lastName + "') called.");
        StringBuilder sbKey = new StringBuilder();
        if (firstName != null) {
            sbKey.append(firstName);
            sbKey.append("##");
        }
        if (lastName != null) {
            sbKey.append(lastName);
        }
        String[] result = archive.get(sbKey.toString());
        if (result == null) {
            result = new String[0];
        }
        // simulate work:
        try {
            Thread.sleep(queryDurationMillis);
        }
        catch (InterruptedException ex) {
            System.out.println(this.getClass().getSimpleName() + ".queryHistoryData('" + firstName + "', '" + lastName
                    +  "') was interrupted!");
        }
        return result;
    }

}
