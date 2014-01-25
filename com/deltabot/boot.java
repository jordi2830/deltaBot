package com.deltabot;

import com.deltabot.core.configurationManager;
import com.deltabot.core.identityManager;
import com.deltabot.core.settings;

public class boot {

    public static void main(String[] args) {

        //Get the identity key and verify it before doing anything else

        for(String argument : args){
            if (argument.startsWith("+")){
                String key = argument.substring(1,argument.length());

                if (identityManager.verifyKey(key)){
                    //The key is valid and is not linked to a banned account, so lets fetch the bot configuration from the server for this key
                    System.out.println("The key '" + key + "' is valid.");

                    configurationManager.loadConfiguration(key);

                } else {
                    System.out.println("The key '" + key + "' is either invalid or linked to a banned account.");
                }
            } else if (argument.startsWith("-")){
                if (argument.substring(1,argument.length()).equals("dev")){
                    settings.developerMode = true;
                    settings.masterServerIP = "localhost";

                    System.out.println("Developermode enabled");
                }

            }
        }

    }
}
