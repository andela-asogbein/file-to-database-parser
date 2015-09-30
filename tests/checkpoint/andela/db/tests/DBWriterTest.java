package checkpoint.andela.db.tests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

import checkpoint.andela.db.DBOperations;
import checkpoint.andela.db.DBWriter;
import checkpoint.andela.parser.Reaction;

public class DBWriterTest {

	@Test
	public void testAddMessageToLogBuffer() throws InterruptedException {
		BlockingQueue<Reaction> sharedQueue = new LinkedBlockingQueue<Reaction>();
		BlockingQueue<String> logSharedQueue = new LinkedBlockingQueue<String>();
		
		DBWriter dbw = new DBWriter(sharedQueue, logSharedQueue);
        Reaction singleReaction = new Reaction();

		assertFalse(sharedQueue.contains(singleReaction));

        singleReaction.put("UNIQUE-ID", "RXN-1748");
        singleReaction.put("TYPES", "NORMAL");
		
        dbw.addMessageToLogBuffer(singleReaction);
		assertTrue(logSharedQueue.peek().contains("---- collected UNIQUE ID RXN-1748 from buffer"));
	}
	
	@Test
	public void testSaveReactionToDatabase() throws SQLException{
		DBOperations dbo = new DBOperations();
        Reaction singleReaction = new Reaction();
        singleReaction.put("UNIQUE-ID", "RXN-1748");
        dbo.saveReactionToDatabase(singleReaction);
        assertTrue(true);
	}
}