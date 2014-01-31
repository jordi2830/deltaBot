package com.deltabot.handlers.game.cod4;

import com.deltabot.handlers.VariableHandler;
import com.deltabot.handlers.game.cod4.events.*;
import com.deltabot.handlers.game.cod4.events.EventHandler.Event;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;


public class LogParser {

    public void loadNewEventElements(String fileLocation) throws IOException {

        ArrayList<EventHandler> logLines = new ArrayList<EventHandler>();
        File logFile = new File(fileLocation);
        Scanner in = new Scanner(logFile);
        LogReader read = new LogReader();
        read.setFileToParse(logFile);

        if (read.parseNeeded()) {
            //Possible FIXME: There might be a one-off error here.
            //skipping through the file
            int i = 0;
            //System.out.println( Integer.parseInt(VariableHandler.getVariableValue("cod4_linecount")) );
            while (i < Integer.parseInt(VariableHandler.getVariableValue("cod4_linecount")) && in.hasNextLine()) {
                in.nextLine();
                i++;
            }

            while (in.hasNextLine()) {
                String k = in.nextLine();
                EventHandler evt = formatString(k);
                //Why would this even work. we create a new object which by default has this
                //set to false
                logLines.add(evt);
            }
        }
        in.close();
        read.updateLineCount();
        pushEventsToAPI(logLines);
    }

    private void pushEventsToAPI(ArrayList<EventHandler> events) {
        for (EventHandler iter : events) {
            iter.pushData();
        }
    }

    private EventHandler formatString(String inString) {
        EventHandler stringArr = new EventHandler(Event.NONE);

        if (inString.contains("InitGame:")) {
            stringArr = formatInitGame(inString);
        } else if (inString.contains("ExitLevel:")) {
            stringArr = formatExitLevel(inString);
        } else if (inString.contains("ShutdownGame:")) {
            stringArr = formatShutdownGame(inString);
        } else if (inString.split(";")[0].endsWith("J")) {
            stringArr = formatJoin(inString);
        } else if (inString.split(";")[0].endsWith("Q")) {
            stringArr = formatQuit(inString);
        } else if (inString.split(";")[0].endsWith("K")) {
            stringArr = formatKill(inString);
        } else if (inString.split(";")[0].endsWith("D")) {
            stringArr = formatDamage(inString);
        } else if (inString.split(";")[0].endsWith("say")) {
            stringArr = formatSay(inString);
        } else if (inString.split(";")[0].endsWith("Weapon")) {
            stringArr = formatWeapon(inString);
        }
        return stringArr;


    }

    private EventHandler formatInitGame(String inString) {
        //First element is header, we don't want that.
        //String[] slicedData = Arrays.copyOfRange(inString., 6, inString.length());

        //Lets first handle the serverInfo, which is the first element of slicedData.
        //Lets split the serverinfo key-valuepairs into an array

        Pattern p = Pattern.compile("\\", Pattern.LITERAL);
        String[] keyvalue = p.split(inString);

        HashMap<String, String> serverInfo = new HashMap<String, String>();
        for (int i = 1; i < keyvalue.length; i += 2) {
            if (i + 1 < keyvalue.length) {
                String key = keyvalue[i];
                String value = keyvalue[i + 1];
                //System.out.println(key + " => "+ value);
                serverInfo.put(key, value);
            }
        }

        serverInfo.put("time", inString.split(" ")[1]);

        EventHandler k = new InitGameEventHandler(Event.INIT_GAME);
        k.setData(serverInfo);
        return k;
    }

    private EventHandler formatExitLevel(String inString) {
        //  8:03 Q;d3d1442eed3e6d4dc24246c00a050914;0;[D.R] SnAg
        Map<String, String> m = new HashMap<String, String>();

        //Unneeded, but doesnt hurt.
        m.put("event", "ExitLevel");

        m.put("time", inString.split(" ")[1]);

        EventHandler k = new ExitLevelEventHandler(Event.EXITLEVEL);
        k.setData(m);

        return k;
    }

    private EventHandler formatShutdownGame(String inString) {
        //  8:03 Q;d3d1442eed3e6d4dc24246c00a050914;0;[D.R] SnAg
        Map<String, String> m = new HashMap<String, String>();

        //Unneeded, but doesnt hurt.
        m.put("event", "ShutdownGame");

        m.put("time", inString.split(" ")[1]);

        EventHandler k = new ShutdownGameEventHandler(Event.SHUTDOWNGAME);
        k.setData(m);

        return k;
    }

    private EventHandler formatJoin(String inString) {
        String[] playerDataArray = inString.split(";");
        //  8:03 Q;d3d1442eed3e6d4dc24246c00a050914;0;[D.R] SnAg
        Map<String, String> m = new HashMap<String, String>();

        m.put("guid", playerDataArray[1]);
        m.put("playerName", playerDataArray[3]);

        m.put("time", inString.split(" ")[1]);

        EventHandler k = new JoinEventHandler(Event.JOIN);
        k.setData(m);

        return k;
    }

    private EventHandler formatQuit(String inString) {
        String[] playerDataArray = inString.split(";");
        //  8:03 Q;d3d1442eed3e6d4dc24246c00a050914;0;[D.R] SnAg
        Map<String, String> m = new HashMap<String, String>();

        m.put("guid", playerDataArray[1]);
        m.put("playerName", playerDataArray[3]);

        m.put("time", inString.split(" ")[1]);

        EventHandler k = new QuitEventHandler(Event.QUIT);
        k.setData(m);

        return k;
    }

    private EventHandler formatSay(String inString) {
        //142:38 say;d3d1442eed3e6d4dc24246c00a050914;0;[D.R] SnAg;QUICKMESSAGE_YES_SIR
        String[] sayData = inString.split(";");
        Map<String, String> k = new HashMap<String, String>();
        k.put("time", inString.split(" ")[1]);

        k.put("guid", sayData[1]);
        k.put("playerName", sayData[3]);
        k.put("message", sayData[4].substring(1));
        EventHandler i = new SayEventHandler(Event.SAY);
        i.setData(k);
        return i;
    }

    private EventHandler formatWeapon(String inString) {
        //Weapon;b31337396d74995b8dde0551917205a1;0;tronds;mp5_mp
        String[] sayData = inString.split(";");
        Map<String, String> k = new HashMap<String, String>();
        k.put("time", inString.split(" ")[1]);
        k.put("guid", sayData[1]);
        k.put("playerName", sayData[3]);
        k.put("weapon", sayData[4]);
        EventHandler i = new WeaponEventHandler(Event.WEAPON);
        i.setData(k);
        return i;
    }

    private EventHandler formatKill(String inString) {
        String[] dataArray = inString.split(";");

        // 65:14 K;78f9bf2bd82e444b19e09585540aded9;0;;cx.4;d3d1442eed3e6d4dc24246c00a050914;1;;[D.R] SnAg;m4_gl_mp;30;MOD_RIFLE_BULLET;torso_lower
        // 109:10 K;d3d1442eed3e6d4dc24246c00a050914;0;;[D.R] SnAg;d3d1442eed3e6d4dc24246c00a050914;-1;;[D.R] SnAg;frag_grenade_mp;367;MOD_GRENADE_SPLASH;none
        //  4:48 K;d3d1442eed3e6d4dc24246c00a050914;0;;[D.R] SnAg;;-1;world;;none;14;MOD_FALLING;none

        //56:37 K;613b44664d49782f04636418b299cb9d;1;;Jordi;b31337396d74995b8dde0551917205a1;0;;tronds;mp44_mp;56;MOD_RIFLE_BULLET;right_arm_upper
        Map<String, String> info = new HashMap<String, String>();

        info.put("time", inString.split(" ")[1]);

        info.put("victim_guid", dataArray[1]);
        info.put("victim_name", dataArray[4]);
        info.put("attacker_guid", dataArray[5]);
        info.put("attacker_name", dataArray[8]);
        info.put("weapon", dataArray[9]);
        info.put("weapon_bullet_type", dataArray[11]);
        info.put("hitlocation", dataArray[12]);

        //System.out.println(inString);

        EventHandler k = new KillEventHandler(Event.KILL);
        k.setData(info);
        return k;
    }

    private EventHandler formatDamage(String inString) {
        //D;613b44664d49782f04636418b299cb9d;0;axis;Jordi;b31337396d74995b8dde0551917205a1;1;axis;tronds;p90_silencer_mp;13;MOD_PISTOL_BULLET;torso_lower
        //D;b31337396d74995b8dde0551917205a1;0;axis;tronds;;-1;world;;none;29;MOD_FALLING;none
        String[] dataArray = inString.split(";");

        Map<String, String> info = new HashMap<String, String>();

        info.put("time", inString.split(" ")[1]);

        info.put("victim_guid", dataArray[1]);
        info.put("victim_name", dataArray[4]);
        info.put("attacker_guid", dataArray[5]);
        info.put("attacker_name", dataArray[8]);
        info.put("weapon", dataArray[9]);
        info.put("weapon_bullet_type", dataArray[11]);
        info.put("hitlocation", dataArray[12]);

        //System.out.println(inString);

        EventHandler k = new DamageEventHandler(Event.DAMAGE);
        k.setData(info);
        return k;
    }
}