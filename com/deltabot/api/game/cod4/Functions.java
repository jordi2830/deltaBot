package com.deltabot.api.game.cod4;

import java.util.ArrayList;
import java.util.List;

public class Functions {

    //Nearly all of this is incomplete due to me not being able to test rcon right now, will be completed later

    public static void sayTo(Player p, String Message){
        p.tell(Message);

    }

    public static void kickPlayer(Player p){
       kickPlayer(p, "NO_REASON_DEFINED");
    }

    public static void kickPlayer(Player p, String reason){
        p.kick(reason);
    }

    public static void banPlayer(Player p, String duration){
        banPlayer(p,duration,"NO_REASON_DEFINED");
    }

    public static void banPlayer(Player p, String duration, String reason){
        p.ban(reason, duration);
    }

    public static String getDvar(String dvar){
        return "some_value";
    }

    public static void setDvar(String dvar, String value){

    }

    public static String getCurrentMap(){
        return "mp_somemap";
    }

    public static List<Player> getCurrentPlayers(){
        return new ArrayList<Player>();
    }

    public static Player getPlayerByName(String name){
        return new Player();
    }

    public static Player getPlayerByNum(int num){
        return new Player();
    }

    public static Player getPlayerByIP(String IP){
        return new Player();
    }

    public static Player getPlayerByGUID(String GUID){
        return new Player();
    }

}
