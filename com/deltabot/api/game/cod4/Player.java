package com.deltabot.api.game.cod4;

import com.deltabot.handlers.RCONHandler;

public class Player {

    public int num;
    public String name;
    public int score;
    public int ping;
    public String GUID;
    public String IP;

    public Player(int num, String name, int score, int ping, String GUID, String IP) {
        this.num = num;
        this.name = name;
        this.score = score;
        this.ping = ping;
        this.GUID = GUID;
        this.IP = IP;
    }

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
