package codelionx.eportfolio.demos.activeobject.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import codelionx.eportfolio.demos.activeobject.DatabaseServant;
import codelionx.eportfolio.demos.activeobject.DatabaseServantMock;

/**
 * Tests the database.
 */
public class TestWithoutAO {
	
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
    
    
	@Test
	public void testQueryData() {
		System.out.println("Test database ...");
        long startTimeNanos = System.nanoTime();

        DatabaseServant database = new DatabaseServantMock(MOCK_HISTORY_DATA, CALL_DURATION);


        // now let's query
        String[] result1 = database.queryData("Jack", "Miller");
        System.out.println("Got result from query1");
        String[] result2 = database.queryData("Lisa", "Miller");
        System.out.println("Got result from query2");
        String[] result3 = database.queryData("Mary", "Gin-Tonic");
        System.out.println("Got result from query3");

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotNull(result3);

        assertEquals("The quick brown fox jumped over the lazy dog.", result1[2]);
        assertEquals("Bad bananas punished by old trees.", result2[2]);
        assertEquals("Blue apes on skyscrapers.", result3[2]);

        System.out.println("Test successful! Elapsed time: " + (System.nanoTime() - startTimeNanos) + " s");
	}

}
