package com.deltabot.api.game.cod4;

import com.deltabot.handlers.PermissionsHandler;
import com.deltabot.handlers.RCONHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Player {

    public String name;

    ;
    public String GUID;

    ;
    private boolean varsSetFromExternalData = false;

    ;
    private int externalData_num;

    ;
    private int externalData_score;
    private int externalData_ping;
    private String externalData_IP;
    public Player(int num, String name, int score, int ping, String GUID, String IP) {
        //This is only called when all of the info is already available (fetched from status request)
        this.name = name;
        this.GUID = GUID;
        this.varsSetFromExternalData = true;
        this.externalData_num = num;
        this.externalData_score = score;
        this.externalData_ping = ping;
        this.externalData_IP = IP;
    }
    public Player(String name, String GUID) {
        this.name = name;
        this.GUID = GUID;
    }

    public int num() {
        //only fetch the num when it is requested by a plugin, else this would result in ALOT of status requests each time an event occured
        //the same goes for ping, score and IP (as these 4 pieces of data are not provided in a log, and must be fetched by rcon)
        if (varsSetFromExternalData) return externalData_num;

        for (Player p : Functions.getCurrentPlayers()) {
            if (p.GUID.equals(GUID)) return p.num();
        }
        return -1;
    }

    public int score() {
        if (varsSetFromExternalData) return externalData_score;

        for (Player p : Functions.getCurrentPlayers()) {
            if (p.GUID.equals(GUID)) return p.score();
        }
        return -1;
    }

    public int ping() {
        if (varsSetFromExternalData) return externalData_ping;

        for (Player p : Functions.getCurrentPlayers()) {
            if (p.GUID.equals(GUID)) return p.ping();
        }
        return -1;
    }

    public String IP() {
        if (varsSetFromExternalData) return externalData_IP;

        for (Player p : Functions.getCurrentPlayers()) {
            if (p.GUID.equals(GUID)) return p.IP();
        }
        return null;
    }

    public void kick() {
        RCONHandler.sendRCON("clientkick " + num());
    }

    public void tell(String message) {
        tellRaw("^1[^3dB^1]^7: " + message);
    }

    public void tellRaw(String message) {
        RCONHandler.sendRCON("tell " + num() + " " + message);
    }

    public int getPermissions() {
        return PermissionsHandler.getPermissions(GUID);
    }

    public String getCountry() {

        try {
            URL url = new URL("http://api.hostip.info/get_html.php?ip=" + IP());
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            return reader.readLine().split(":")[1].split("\\(")[0].trim();

        } catch (MalformedURLException e) {
            return "null";
        } catch (IOException e) {
            return "null";
        }
    }
}
