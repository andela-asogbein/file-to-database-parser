package checkpoint.andela.parser.tests;

import static org.junit.Assert.*;

import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

import checkpoint.andela.parser.Reaction;
import checkpoint.andela.parser.ReactionParser;
import checkpoint.andela.parser.ReadFile;

public class FileParserTest {
		
	@Test
	public void testParseReactionAttributes() throws Exception{        
		BlockingQueue<Reaction> sharedQueue = new LinkedBlockingQueue<Reaction>();
		BlockingQueue<String> logSharedQueue = new LinkedBlockingQueue<String>();
		ReactionParser rp = new ReactionParser(sharedQueue, logSharedQueue);

		ReadFile fp = new ReadFile(rp);
		
		fp.readFile("test2.txt");
		assertTrue(sharedQueue.size() == 2);
		assertFalse(logSharedQueue.isEmpty());
	}
	
	@Test
	public void testParseReactionAttributesForEmptyFile() throws Exception{        
		BlockingQueue<Reaction> sharedQueue = new LinkedBlockingQueue<Reaction>();
		BlockingQueue<String> logSharedQueue = new LinkedBlockingQueue<String>();
		ReactionParser rp = new ReactionParser(sharedQueue, logSharedQueue);

		ReadFile fp = new ReadFile(rp);
		
		fp.readFile("test1.txt");
		assertTrue(sharedQueue.isEmpty());
		assertTrue(logSharedQueue.isEmpty());
	}
	
	@Test
	public void testAddTimeStampAndUniqueIdToLogBuffer() throws InterruptedException{
		BlockingQueue<Reaction> sharedQueue = new LinkedBlockingQueue<Reaction>();
		BlockingQueue<String> logSharedQueue = new LinkedBlockingQueue<String>();
		ReactionParser rp = new ReactionParser(sharedQueue, logSharedQueue);

		Reaction singleReaction = rp.getSingleReaction();

		assertFalse(sharedQueue.contains(singleReaction));

        singleReaction.put("UNIQUE-ID", "RXN-1748");
        singleReaction.put("TYPES", "NORMAL");
        rp.addTimeStampAndUniqueIdToLogBuffer();
		assertTrue(logSharedQueue.peek().contains("---- wrote UNIQUE ID RXN-1748 to buffer"));
	}
	
	@Test
	public void testAddSingleReactionToBuffer() throws InterruptedException{
		BlockingQueue<Reaction> sharedQueue = new LinkedBlockingQueue<Reaction>();
		BlockingQueue<String> logSharedQueue = new LinkedBlockingQueue<String>();
		ReactionParser rp = new ReactionParser(sharedQueue, logSharedQueue);
        Reaction singleReaction = rp.getSingleReaction();

		assertFalse(sharedQueue.contains(singleReaction));

        singleReaction.put("UNIQUE-ID", "RXN-1748");
        singleReaction.put("TYPES", "NORMAL");
		
        rp.addSingleReactionToBuffer();
			
		assertTrue(sharedQueue.contains(singleReaction));
	}
}