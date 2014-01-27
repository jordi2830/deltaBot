package com.deltabot.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LogReader {
	
	private File fileToParse;
	private int lineCount = 0;
	
	public void setFileToParse(File inFile){
		fileToParse = inFile;
	}
	
	public File getFileToParse(){
		return fileToParse;
	}
	
	public boolean parseNeeded(){
		
		int currLine;
		currLine = getLineCount(fileToParse);
		if(currLine != lineCount){
			//File was found, different amount of lines from last parse
			//Reparse needed
			lineCount = currLine;
			return true;
		} else {
			//File was found, same amount of lines as from last parse
			//No reparse needed
			lineCount = currLine;
			return false;
		}
	}
	
	public int getLineCount(File inFile){
		
		int i = 0;
	
		try {
			Scanner in = new Scanner(inFile);
			while(in.hasNextLine()){
				i++;
				in.nextLine();
			}
			
			in.close();
			
			return i;
		} catch (FileNotFoundException e) {
			
			//We return 0 as we don't got a file to parse.
			return 0;
		}
	}
}
