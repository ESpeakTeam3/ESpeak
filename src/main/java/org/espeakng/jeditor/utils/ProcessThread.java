package org.espeakng.jeditor.utils;

import java.io.IOException;
import java.util.Arrays;


import org.apache.log4j.Logger;
import org.espeakng.jeditor.gui.EspeakNg;

public class ProcessThread implements Runnable {
	private static Logger logger = Logger.getLogger(ProcessThread.class.getName());
	private String[] command;
	public static boolean stop = false;
	
	public ProcessThread(String[] command) {
		this.command = command;
	}

	@Override
	public void run() {
		try {
			Process pb = Runtime.getRuntime().exec(command);
			pb.waitFor();
			
			String output = CommandUtilities.getOutput(pb);
			String error = CommandUtilities.getError(pb);
	        
		
			
			if (!(output.equals("")))
                logger.info("executeCmd(" + Arrays.toString(command) + ")\nOutput message: " + output);
	        else
	                logger.info("executeCmd(" + Arrays.toString(command) + ") executed successfully");
	
	        if (error != "")
	                logger.fatal(error);
			 
		} catch (IOException e) {
			logger.warn(e);
		} catch (InterruptedException e) {
			logger.warn(e);
		}
		return;
	}
	
	public Logger getLogger(){
		return logger;
	}

}
