package checkpoint.andela.parser;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import checkpoint.andela.db.DBWriter;
import checkpoint.andela.log.LogWriter;

public class TestThread {
	
	public static void main(String args[]) throws Exception {
		BlockingQueue<Reaction> sharedQueue = new LinkedBlockingQueue<Reaction>();
		BlockingQueue<String> logSharedQueue = new LinkedBlockingQueue<String>();
	
		ReactionParser rp = new ReactionParser(sharedQueue, logSharedQueue);
		
		Thread fileParserThread = new Thread(new ReadFile(rp));
		Thread dbWriterThread = new Thread(new DBWriter(sharedQueue, logSharedQueue));
		Thread logWriterThread = new Thread(new LogWriter(logSharedQueue));
		
		fileParserThread.start();
		dbWriterThread.start();
		logWriterThread.start();
	}
}