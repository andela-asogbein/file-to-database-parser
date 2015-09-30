package checkpoint.andela.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReadFile implements Runnable {
	
	private Parser reactionParser;
	
	public ReadFile(Parser parser) {
		reactionParser = parser;
	}

	@Override
	public void run() {
		try {
			readFile("reactions.dat");
		}
		catch(Exception e) {
            e.printStackTrace();
		}
	}
	
	public void readFile(String fileToBeParsed) throws Exception {
      	BufferedReader  reader = new BufferedReader(new FileReader(new File(fileToBeParsed)));
        String lineInFile;
	        while ((lineInFile = reader.readLine()) != null) {
	        	reactionParser.parseLine(lineInFile);
	        }
        reader.close();
	}
}