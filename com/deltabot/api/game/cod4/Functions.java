package com.deltabot.api.game.cod4;

import com.deltabot.handlers.RCONHandler;

import java.util.ArrayList;
import java.util.List;

public class Functions {

    public static void sayTo(Player p, String Message) {
        p.tell(Message);

    }

    public static void kickPlayer(Player p) {
        kickPlayer(p, "NO_REASON_DEFINED");
    }

    public static void kickPlayer(Player p, String reason) {
        p.kick(reason);
    }

    public static void banPlayer(Player p, String duration) {
        banPlayer(p, duration, "NO_REASON_DEFINED");
    }

    public static void banPlayer(Player p, String duration, String reason) {
        p.ban(reason, duration);
    }

    public static String getDvar(String dvar) {
        String responseValue = RCONHandler.sendRCON(dvar).split("\"")[3]; //split on " and get the 4th array part as its the value
        return responseValue.substring(0,responseValue.length() - 2 ); //cut of the last 2 chars, as they are always "^7"
    }

    public static void setDvar(String dvar, String value) {
        RCONHandler.sendRCON("seta " + dvar + " " + value);
    }

    public static String getCurrentMap() {
        return getDvar("mapname");
    }

    public static List<Player> getCurrentPlayers() {
        return new ArrayList<Player>();
    }

    public static Player getPlayerByName(String name) {
        return new Player();
    }

    public static Player getPlayerByNum(int num) {
        return new Player();
    }

    public static Player getPlayerByIP(String IP) {
        return new Player();
    }

    public static Player getPlayerByGUID(String GUID) {
        return new Player();
    }

}
