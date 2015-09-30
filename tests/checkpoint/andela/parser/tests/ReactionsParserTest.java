package checkpoint.andela.parser.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

import checkpoint.andela.parser.Reaction;
import checkpoint.andela.parser.ReactionParser;

public class ReactionsParserTest {
	@Test
	public void testIsValidLine(){
		BlockingQueue<Reaction> sharedQueue = new LinkedBlockingQueue<Reaction>();
		BlockingQueue<String> logSharedQueue = new LinkedBlockingQueue<String>();
		
		ReactionParser fp = new ReactionParser(sharedQueue, logSharedQueue);
		
		assertFalse(fp.isValidLine("#"));
		assertFalse(fp.isValidLine(""));
		assertTrue(fp.isValidLine(" - "));
		assertFalse(fp.isValidLine("UNIQUE-ID"));
	}
	
	@Test
	public void testLineHasselectedAttributes() {
		BlockingQueue<Reaction> sharedQueue = new LinkedBlockingQueue<Reaction>();
		BlockingQueue<String> logSharedQueue = new LinkedBlockingQueue<String>();
		
		ReactionParser fp = new ReactionParser(sharedQueue, logSharedQueue);
		
		assertTrue(fp.lineHasselectedAttributes("UNIQUE-ID"));
		assertTrue(fp.lineHasselectedAttributes("TYPES"));
		assertTrue(fp.lineHasselectedAttributes("COMMON-NAME"));
		assertTrue(fp.lineHasselectedAttributes("ATOM-MAPPINGS"));
		assertTrue(fp.lineHasselectedAttributes("CANNOT-BALANCE?"));
		assertTrue(fp.lineHasselectedAttributes("ENZYMATIC-REACTION"));
		assertTrue(fp.lineHasselectedAttributes("ORPHAN?"));
		assertFalse(fp.lineHasselectedAttributes("LEFT"));
		
	}
	
	@Test
	public void testAddAttributesForReaction(){
		BlockingQueue<Reaction> sharedQueue = new LinkedBlockingQueue<Reaction>();
		BlockingQueue<String> logSharedQueue = new LinkedBlockingQueue<String>();
		
		String s = "UNIQUE-ID - HNSK74934";
		ReactionParser rp = new ReactionParser(sharedQueue, logSharedQueue);
		TreeMap<String, String> singleReaction = rp.getSingleReaction();
		assertTrue(singleReaction.size() == 0);
		rp.addAttributesForReaction(s);
		assertTrue(singleReaction.size() == 1);
	}
	
	@Test
	public void testAddAttributesForReactionForInvalidString(){
		BlockingQueue<Reaction> sharedQueue = new LinkedBlockingQueue<Reaction>();
		BlockingQueue<String> logSharedQueue = new LinkedBlockingQueue<String>();
		String s = "UNIQUEIDHNSK74934";
		ReactionParser rp = new ReactionParser(sharedQueue, logSharedQueue);
		TreeMap<String, String> singleReaction = rp.getSingleReaction();
		assertTrue(singleReaction.size() == 0);
	}
	
	@Test
	public void testSingleReaction(){
		BlockingQueue<Reaction> sharedQueue = new LinkedBlockingQueue<Reaction>();
		BlockingQueue<String> logSharedQueue = new LinkedBlockingQueue<String>();
		ReactionParser rp = new ReactionParser(sharedQueue, logSharedQueue);
		TreeMap<String, String> singleReaction = rp.getSingleReaction();
		rp.createNewReaction();
		TreeMap<String, String> singleReaction2 = rp.getSingleReaction();
		assertFalse(singleReaction == singleReaction2);
	}
	
	@Test
	public void testIsFull(){
		BlockingQueue<Reaction> sharedQueue = new LinkedBlockingQueue<Reaction>();
		BlockingQueue<String> logSharedQueue = new LinkedBlockingQueue<String>();
		ReactionParser rp = new ReactionParser(sharedQueue, logSharedQueue);
		String s = "//";
		assertTrue(rp.isFull(s));
	}
	
	@Test
	public void testIsFull2(){
		BlockingQueue<Reaction> sharedQueue = new LinkedBlockingQueue<Reaction>();
		BlockingQueue<String> logSharedQueue = new LinkedBlockingQueue<String>();
		ReactionParser rp = new ReactionParser(sharedQueue, logSharedQueue);
		String s = "UJNFDN//";
		assertFalse(rp.isFull(s));
	}
	
	@Test
	public void testParseLine(){
		BlockingQueue<Reaction> sharedQueue = new LinkedBlockingQueue<Reaction>();
		BlockingQueue<String> logSharedQueue = new LinkedBlockingQueue<String>();
		String s = "UNIQUE-ID - HNSK74934";
		ReactionParser rp = new ReactionParser(sharedQueue, logSharedQueue);
		TreeMap<String, String> singleReaction = rp.getSingleReaction();
		assertTrue(singleReaction.size() == 0);
		rp.parseLine(s);
		assertTrue(singleReaction.size() == 1);
	}
}
