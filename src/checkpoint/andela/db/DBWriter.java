package checkpoint.andela.db;

import java.sql.Timestamp;
import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;

import checkpoint.andela.parser.Reaction;

public class DBWriter implements Runnable{

	private BlockingQueue<Reaction> bufferSharedQueue;
	private BlockingQueue<String> logBufferSharedQueue;
	private DBOperations dbo;
	
	public DBWriter(BlockingQueue<Reaction> sharedQueue, BlockingQueue<String> logSharedQueue){
		this.bufferSharedQueue = sharedQueue;
		this.logBufferSharedQueue = logSharedQueue;
		dbo = new DBOperations();
	}

	@Override
	public void run() {
		try {
			while (true) {
				Reaction singleReaction = bufferSharedQueue.take();
				addMessageToLogBuffer(singleReaction);
				dbo.saveReactionToDatabase(singleReaction);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addMessageToLogBuffer(Reaction singleReaction) throws InterruptedException{
		logBufferSharedQueue.put("DBWriter Thread ("+ new Timestamp(new Date().getTime()) + ")---- "
		         	+ "collected UNIQUE ID " + singleReaction.get("UNIQUE-ID") + " from buffer");
	}
}