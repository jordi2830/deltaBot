package com.deltabot.api.game.cod4;

import com.deltabot.handlers.RCONHandler;

import java.util.ArrayList;
import java.util.List;

public class Functions {

    public static void sayTo(Player p, String Message) {
        p.tell(Message);

    }

    public static void sayAll(String Message) {
        RCONHandler.sendRCON("say ^1[^3deltaBot^1]^7: " + Message);
    }

    public static void kickPlayer(Player p) {
        kickPlayer(p, "NO_REASON_DEFINED");
    }

    public static void kickPlayer(Player p, String reason) {
        p.kick(reason);
    }

    public static String getDvar(String dvar) {
        String responseValue = RCONHandler.sendRCON(dvar).split("\"")[3]; //split on " and get the 4th array part as its the value
        return responseValue.substring(0, responseValue.length() - 2); //cut of the last 2 chars, as they are always "^7"
    }

    public static void setDvar(String dvar, String value) {
        RCONHandler.sendRCON("seta " + dvar + " " + value);
    }

    public static String getCurrentMap() {
        return getDvar("mapname");
    }

    public static List<Player> getCurrentPlayers() {
        String statusresponseData[] = RCONHandler.sendRCON("status").split(System.getProperty("line.separator")); //use this line separator since its OS independent

        if (statusresponseData.length == 6) {
            //6 = length of statusresponse from empty server, so return an empty list now, before attempting any parsing
            return new ArrayList<Player>();
        }

        List<Player> playerList = new ArrayList<Player>();

        for (int i = 4; i <= statusresponseData.length - 3; i++) {

            String currentPlayerData = statusresponseData[i];

            //Start with the num -> char 2 & 3
            int num = Integer.valueOf(currentPlayerData.substring(1, 2).replace(" ", ""));
            int score = Integer.valueOf(currentPlayerData.substring(4, 8).replace(" ", ""));
            int ping = Integer.valueOf(currentPlayerData.substring(10, 13).replace(" ", "")); //FIXME: could be bugged, needs to be tested
            String GUID = currentPlayerData.substring(15, 46);
            String name = "";

            if (currentPlayerData.substring(48, 62).split("^").length > 1) {
                //The player has color codes , which makes it a bit trickier to parse it..
                String nameChars = currentPlayerData.substring(48, 62);

                for (; ; ) {
                    //Check if the last char is a empty space, if so, cut it off and check again
                    if (nameChars.charAt(nameChars.length()) != " ".charAt(0)) {
                        name = nameChars;
                        break;
                    } else {
                        nameChars = nameChars.substring(0, nameChars.length() - 1);
                    }
                }

            } else {
                name = currentPlayerData.substring(48, 62).split("^")[0];
            }

            String IP = currentPlayerData.substring(74, 92).split(":")[0];

            Player p = new Player(num, name, score, ping, GUID, IP);

            playerList.add(p);

        }

        return playerList;
    }

    public static Player getPlayerByName(String name) {
        for (Player p : getCurrentPlayers()) {
            if (p.name.toLowerCase().equals(name.toLowerCase())) return p;
        }

        return null;
    }

    public static Player getPlayerByNum(int num) {
        for (Player p : getCurrentPlayers()) {
            if (p.num == num) return p;
        }

        return null;
    }

    public static Player getPlayerByIP(String IP) {
        for (Player p : getCurrentPlayers()) {
            if (p.IP.equals(IP)) return p;
        }

        return null;
    }

    public static Player getPlayerByGUID(String GUID) {

        for (Player p : getCurrentPlayers()) {
            if (p.GUID.equals(GUID)) return p;
        }

        return null;
    }

}
