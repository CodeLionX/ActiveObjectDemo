package codelionx.eportfolio.demos.activeobject.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import codelionx.eportfolio.demos.activeobject.AbstractHistoryQueryEngine;
import codelionx.eportfolio.demos.activeobject.DatabaseProxy;
import codelionx.eportfolio.demos.activeobject.HistoryQueryEngineMock;
import codelionx.eportfolio.demos.activeobject.HistoryQueryScheduler;
import codelionx.eportfolio.demos.activeobject.QueryRequestFuture;

/**
 * Active Object Test - demonstrates ACTIVE OBJECT pattern.
 */
public class ActiveObjectTest {

    /**
     * some test data
     */
    private static final List<String[]> MOCK_HISTORY_DATA;
    static {
        MOCK_HISTORY_DATA = new ArrayList<>();
        MOCK_HISTORY_DATA.add(new String[] { "Jack", "Miller", "The quick brown fox jumped over the lazy dog." });
        MOCK_HISTORY_DATA.add(new String[] { "Lisa", "Miller", "Bad bananas punished by old trees." });
        MOCK_HISTORY_DATA.add(new String[] { "Mary", "Gin-Tonic", "Blue apes on skyscrapers." });
    }

    /**
     * test duration for calls
     */
    private static final long CALL_DURATION = 5000L;

    /**
     * number of worker threads for test
     */
    private static final int NUMBER_OF_WORKERS = 2; // Runtime.getRuntime().availableProcessors()

    @Test
    public void testActiveObject() throws Exception {

        System.out.println("Test Active Object ...");
        long startTimeNanos = System.nanoTime();

        AbstractHistoryQueryEngine engine = new HistoryQueryEngineMock(MOCK_HISTORY_DATA, CALL_DURATION);
        HistoryQueryScheduler scheduler = new HistoryQueryScheduler(NUMBER_OF_WORKERS);

        DatabaseProxy database = new DatabaseProxy(engine, scheduler);

        // now let's query
        QueryRequestFuture future1 = database.queryData("Jack", "Miller");
        QueryRequestFuture future2 = database.queryData("Lisa", "Miller");
        QueryRequestFuture future3 = database.queryData("Mary", "Gin-Tonic");

        while (!future1.isQueryDone() || !future2.isQueryDone() || !future3.isQueryDone()) {
            Thread.sleep(2000);
        }

        String[] result1 = future1.getResult();
        String[] result2 = future2.getResult();
        String[] result3 = future3.getResult();

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotNull(result3);

        assertEquals("The quick brown fox jumped over the lazy dog.", result1[2]);
        assertEquals("Bad bananas punished by old trees.", result2[2]);
        assertEquals("Blue apes on skyscrapers.", result3[2]);

        System.out.println("Test Active Object successful! Elapsed time: " + (System.nanoTime() - startTimeNanos) + " s");

    }

    @Test
    public void testActiveObjectCancel() throws Exception {

        System.out.println("Test Active Object cancel ...");
        long startTimeNanos = System.nanoTime();

        AbstractHistoryQueryEngine engine = new HistoryQueryEngineMock(MOCK_HISTORY_DATA, 10000); // 10 seconds
        HistoryQueryScheduler scheduler = new HistoryQueryScheduler(1); // one call in parallel

        DatabaseProxy queryComponent = new DatabaseProxy(engine, scheduler);

        // now let's query
        QueryRequestFuture future1 = queryComponent.queryData("Jack", "Miller");

        int waitCount = 0;
        while (!future1.isQueryDone()) {
            if (waitCount >= 3) {
                future1.cancelQuery();
                break;
            }
            Thread.sleep(2000);
            waitCount++;
        }

        assertTrue(future1.isQueryCancelled());
        assertNull(future1.getResult());

        System.out.println("Test Active Object cancel successful! Elapsed time: " + (System.nanoTime() - startTimeNanos) + " s");

    }

}
