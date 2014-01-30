package com.deltabot;

import com.deltabot.handlers.game.cod4.LogParser;
import com.deltabot.handlers.game.cod4.PluginHandler;
import com.deltabot.handlers.VariableHandler;

import java.io.IOException;
import java.net.MalformedURLException;

public class Boot {

    public static void main(String[] args) {

        int currentArg = -1;

        for (String argument : args) {
            currentArg++;

            if (argument.startsWith("+")) {
                argument = argument.substring(1, argument.length());

                if (argument.startsWith("set")) {
                    String variable = args[currentArg + 1];
                    String value = args[currentArg + 2];
                    VariableHandler.setVariable(variable, value);
                }

            }

        }

        try {
            //plugin loading attempt below
            PluginHandler.loadPlugin("file:C:\\Users\\Jordi\\Desktop\\ExamplePlugin.jar", "com.jordidp.Plugin");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        LogParser cod4parser = new LogParser();

        for(;;){

            try {
                cod4parser.loadNewEventElements("C:\\Program Files (x86)\\Steam\\SteamApps\\common\\Call of Duty 4\\main\\games_mp.log");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }



    }
}
