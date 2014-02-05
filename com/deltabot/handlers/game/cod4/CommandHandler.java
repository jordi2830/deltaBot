package com.deltabot.handlers.game.cod4;

import com.deltabot.api.game.cod4.Functions;
import com.deltabot.api.game.cod4.Player;
import com.deltabot.core.Settings;
import com.deltabot.handlers.TimeHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler {

    private static List<Map<String, String>> knownCommands = new ArrayList<Map<String, String>>();

    private static void registerCommand(String command, int requiredPermissions) {
        Map<String, String> cmd = new HashMap<String, String>();
        cmd.put("command", command);
        cmd.put("permissions", String.valueOf(requiredPermissions));
        knownCommands.add(cmd);
    }

    public static void init() {

        registerCommand("plugin", 80); //cmd, required permissions level
        registerCommand("b3", 1);
        registerCommand("deltabot", 1);
        registerCommand("db", 1);
        registerCommand("uptime", 1);
        registerCommand("up", 1);
        registerCommand("version", 1);
        registerCommand("lastseen", 1);
        registerCommand("seen", 1);
        registerCommand("group", 1);
        registerCommand("add", 1);
        registerCommand("map", 50);
        registerCommand("country", 1);
    }

    public static boolean isKnownCommand(String command) {
        for (Map<String, String> c : knownCommands) {
            if (command.startsWith(c.get("command"))) {
                return true;
            }
        }
        return false;
    }

    private static int getPermissionsLevelForCommand(String command) {
        for (Map<String, String> c : knownCommands) {
            if (command.startsWith(c.get("command"))) {
                return Integer.valueOf(c.get("permissions"));
            }
        }
        return -1;
    }

    public static void executeCommand(String command, Player caller) {
        if (caller.getPermissions() >= getPermissionsLevelForCommand(command)) {

            if (command.equals("b3") || command.equals("deltabot") || command.equals("db")) {
                Functions.sayAll("This server is running ^3deltaBot version " + Settings.version);
            }

            if (command.equals("uptime") || command.equals("up")) {
                int uptime = TimeHandler.uptime();

                int days = 0;
                int hours = 0;
                int minutes = 0;
                int seconds = 0;

                while (uptime >= 60) {

                    if (uptime >= 86400) {
                        days++;
                        uptime -= 86400;
                    } else if (uptime >= 3600) {
                        hours++;
                        uptime -= 3600;
                    } else if (uptime >= 60) {
                        minutes++;
                        uptime -= 60;
                    }
                }

                seconds = uptime;

                String timeString = "";

                if (days > 1) {
                    timeString = timeString + days + " days, ";
                } else if (days == 1) {
                    timeString = timeString + days + " day, ";
                }

                if (hours > 1) {
                    timeString = timeString + hours + " hours, ";
                } else if (hours == 1) {
                    timeString = timeString + hours + " hour, ";
                }

                if (minutes > 1) {
                    timeString = timeString + minutes + " minutes and ";
                } else if (minutes == 1) {
                    timeString = timeString + minutes + " minute and ";
                }

                if (seconds == 1) {
                    timeString = timeString + seconds + " second";
                } else {
                    timeString = timeString + seconds + " seconds";
                }

                Functions.sayAll("^3deltaBot^7 has been running for ^3" + timeString);

            }

            if (command.startsWith("country")) {
                if (command.contains(" ") && command.split(" ").length == 2) {
                    Player p = Functions.getPlayerByName(command.split(" ")[1]);
                    caller.tell("The country of ^3" + p.name + "^7 is ^3" + p.getCountry());
                } else {
                    caller.tell("Usage: ^3!country <name>");
                }
            }

        } else {
            caller.tell("You have insufficient permissions to execute this command!");
        }
    }


}
