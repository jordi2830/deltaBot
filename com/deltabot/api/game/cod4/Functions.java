package com.deltabot.api.game.cod4;

import com.deltabot.handlers.RCONHandler;

import java.util.ArrayList;
import java.util.List;

public class Functions {

    public static void sayTo(Player p, String Message) {
        p.tell(Message);

    }

    public static void sayAll(String Message) {
        RCONHandler.sendRCON("say ^1[^3dB^1]^7: " + Message);
    }

    public static void kickPlayer(Player p) {
        p.kick();
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

        ArrayList<Player> playerList = new ArrayList<Player>();

        String statusresponse = RCONHandler.sendRCON("status");
        String[] lines = statusresponse.split("\n");

        /* From what I can see, you need to subtract one less than the current
         * playercount from the lines.length variable to get it working properly.
         */

        for (int i = 4; i < lines.length; i++) {
            String[] splitData = lines[i].replaceAll("[ ]+", " ").trim().split(" ");

            int num = -1;
            if (!splitData[0].equals("CNCT")) {
                num = Integer.valueOf(splitData[0]);
            }
            int score = Integer.valueOf(splitData[1]);
            int ping = Integer.valueOf(splitData[2]);
            String guid = splitData[3];

            //Might be a problem here, as lastmsg seems
            //to stick onto the name, without any spaces between the
            //name and the lastmsg

            String name = splitData[4].substring(0, splitData[4].length() - 2); //remove the ^7 at the end of the names
            String lastmsg = splitData[5];
            String address = splitData[6].split(":")[0];
            String qport = splitData[7];
            String rate = splitData[8];

            Player p = new Player(num, name, score, ping, guid, address);
            playerList.add(p);

        }

        return playerList;
    }

    public static Player getPlayerByName(String namePart) {

        int matches = 0;
        Player player = null;

        for (Player p : getCurrentPlayers()) {
            if (p.name.toLowerCase().contains(namePart)) {
                player = p;
            }
        }

        if (matches > 1) return null; //More than one result, return nothing

        return player;
    }

    public static Player getPlayerByNum(int num) {
        for (Player p : getCurrentPlayers()) {
            if (p.num() == num) return p;
        }

        return null;
    }

    public static Player getPlayerByIP(String IP) {
        for (Player p : getCurrentPlayers()) {
            if (p.IP().equals(IP)) return p;
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
