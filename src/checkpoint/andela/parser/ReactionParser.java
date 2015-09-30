package checkpoint.andela.parser;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.BlockingQueue;

public class ReactionParser implements Parser{
	
	private BlockingQueue<Reaction> bufferSharedQueue;
	private BlockingQueue<String> logBufferSharedQueue;
	
    private Reaction singleReaction;
    
    public ReactionParser(BlockingQueue<Reaction> sharedQueue, BlockingQueue<String> logSharedQueue){
    	this.bufferSharedQueue = sharedQueue;
    	this.logBufferSharedQueue = logSharedQueue;
		singleReaction = new Reaction();
    }
    
    @Override
	public void parseLine(String lineInFile) {
	   	 if (isValidLine(lineInFile) && lineHasselectedAttributes(lineInFile)) {
			addAttributesForReaction(lineInFile);
	   	 }
	   	 else if(isFull(lineInFile)){
	   		addSingleReactionToBuffer();
			addTimeStampAndUniqueIdToLogBuffer();
			createNewReaction();
	   	 }
	}
    
    public void addAttributesForReaction(String lineInFile){
		String[] keyValuePair = lineInFile.split(" - ");
		if(keyValuePair != null){
			singleReaction.put(keyValuePair[0], keyValuePair[1]);				
		}
	}
    
    public void addSingleReactionToBuffer() {
		try {
			bufferSharedQueue.put(singleReaction);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void addTimeStampAndUniqueIdToLogBuffer() {
		try {
			logBufferSharedQueue.put("FileParser Thread ("+ new Timestamp(new Date().getTime()) + ")---- "
				     	+ "wrote UNIQUE ID " + singleReaction.get("UNIQUE-ID") + " to buffer");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isValidLine(String lineInFile) {
		LineAttributes lineAttributes = new LineAttributes();
		return lineAttributes.isValidLine(lineInFile);
	}
	
	public boolean lineHasselectedAttributes(String lineInFile) {
		LineAttributes lineAttributes = new LineAttributes();
		return lineAttributes.lineHasselectedAttributes(lineInFile);
	}
	
	public boolean isFull(String lineInFile){
		if(lineInFile.startsWith("//")){
			return true;
		}
		return false;
	}

	public Reaction getSingleReaction() {
		return singleReaction;
	}
	
	public void createNewReaction() {
		singleReaction = new Reaction();
	}
}