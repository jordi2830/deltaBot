package com.deltabot.handlers.game.cod4;

import com.deltabot.handlers.VariableHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LogReader {

    private File fileToParse;

    public File getFileToParse() {
        return fileToParse;
    }

    public void setFileToParse(File inFile) {
        fileToParse = inFile;
    }

    public boolean parseNeeded() {

        int currLine;
        currLine = getFileLineCount(fileToParse);
        if (!VariableHandler.variableExists("cod4_linecount")) {
            VariableHandler.setVariable("cod4_linecount", String.valueOf(getFileLineCount(fileToParse)));
        }
        if (currLine != Integer.valueOf(VariableHandler.getVariableValue("cod4_linecount"))) {
            //File was found, different amount of lines from last parse
            //Reparse needed
            //lineCount = currLine;
            return true;
        } else {
            //File was found, same amount of lines as from last parse
            //No reparse needed
            //lineCount = currLine;
            return false;
        }
    }

    public void updateLineCount() {
        VariableHandler.setVariable("cod4_linecount", String.valueOf(getFileLineCount(fileToParse)));
    }

    private int getFileLineCount(File inFile) {

        int i = 0;

        try {
            Scanner in = new Scanner(inFile);
            while (in.hasNextLine()) {
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
