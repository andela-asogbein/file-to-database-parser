package checkpoint.andela.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;

public class LogWriter implements Runnable{
	
	private BlockingQueue<String> logBufferSharedQueue;
	
	public LogWriter(BlockingQueue<String> logBufferSharedQueue){
		this.logBufferSharedQueue = logBufferSharedQueue;
	}

	@Override
	public void run() {
		try{
			File file = createFile("logfile.txt");
			writeLogToFile(file);		
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public File createFile(String filename) throws IOException{
		File file = new File(filename);
		if(!file.exists()){
			file.createNewFile();
		}
		return file;
	}
	
	public void writeLogToFile(File file) throws InterruptedException, IOException{
		
		FileWriter fileWriter = new FileWriter(file, true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		PrintWriter printWriter = new PrintWriter(bufferedWriter);
		
		while(true){
			String lineInLogFile = removeLineFromLog();
			printWriter.println(lineInLogFile);
			System.out.println(lineInLogFile);
		}
	}
	
	public String removeLineFromLog() throws InterruptedException{
		return logBufferSharedQueue.take();
	}
}