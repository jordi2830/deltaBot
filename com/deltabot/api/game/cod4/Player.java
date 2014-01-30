package com.deltabot.api.game.cod4;

import com.deltabot.handlers.RCONHandler;

public class Player {

    public int num = -1;
    public String name = "null";
    public int Score = -1;
    public int Ping = -1;
    public String GUID = "null";
    public String IP = "null";

    public void kick(String reason) {
        kick(reason, "deltabot_local");
    }

    public void kick(String reason, String caller) {
        RCONHandler.sendRCON("clientkick " + num);
    }

    public void ban(String reason, String duration) {
        ban(reason, duration, "deltabot_local");
    }

    public void ban(String reason, String duration, String caller) {

    }

    public void tell(String message) {

    }
}
