package com.deltabot;

import com.deltabot.handlers.VariableHandler;

public class Boot {

    public static void main(String[] args) {

        int currentArg = -1;

        for(String argument : args){
            currentArg++;

            if (argument.startsWith("+")){
                argument = argument.substring(1, argument.length());

                if (argument.startsWith("set")){
                    String variable = args[currentArg + 1];
                    String value = args[currentArg + 2];
                    VariableHandler.setVariable(variable, value);
                }

            }

        }

    }
}
