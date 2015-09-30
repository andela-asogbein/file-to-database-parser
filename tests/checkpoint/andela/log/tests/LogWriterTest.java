package checkpoint.andela.log.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Test;

import checkpoint.andela.log.LogWriter;

public class LogWriterTest {
	
	@Test
	public void testCreateFile() throws IOException{
		BlockingQueue<String> logBufferSharedQueue = new LinkedBlockingQueue<String>();
		LogWriter lw = new LogWriter(logBufferSharedQueue);
		
		File file = lw.createFile("le.txt");
		file.createNewFile();
		assertTrue(file.exists());
		file = lw.createFile("test1.txt");
		assertTrue(file.exists());
	}

	@Test
	public void testWriteLogToFile() throws InterruptedException, IOException {
		BlockingQueue<String> logBufferSharedQueue = new LinkedBlockingQueue<String>();
		String lineInLog = "FileParser Thread (2015-09-17 22:55:58.68)---- wrote UNIQUE ID 6-PHOSPHO-BETA-GALACTOSIDASE-RXN to buffer";
		logBufferSharedQueue.add(lineInLog);

		LogWriter lw = new LogWriter(logBufferSharedQueue);

		File f = lw.createFile("test3.txt");
		assertTrue(logBufferSharedQueue.contains(lineInLog));
		lw.removeLineFromLog();
		assertFalse(logBufferSharedQueue.contains(lineInLog));
	}
}