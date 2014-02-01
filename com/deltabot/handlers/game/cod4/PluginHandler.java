package com.deltabot.handlers.game.cod4;

import com.deltabot.api.game.cod4.Player;
import com.deltabot.api.game.cod4.PluginInterface;
import com.deltabot.handlers.game.cod4.events.EventHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PluginHandler {

    private static List<Plugin> loadedPlugins = new ArrayList<Plugin>();

    public static void loadPlugin(String jar, String pluginMainClass) throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader cl = new URLClassLoader(new URL[]{new URL(jar)});
        PluginInterface newPlugin = (PluginInterface) cl.loadClass(pluginMainClass).newInstance();

        Plugin p = new Plugin(newPlugin); //Plugin will be started once its object is created

        loadedPlugins.add(p);

    }

    public static void raiseEvent(EventHandler e, Map<String, String> eventData) {
        for (Plugin p : loadedPlugins) {
            p.sendEvent(e, eventData); //TODO: handle events
        }
    }

}

enum PLUGINSTATE {
    RUNNING, PAUSED, STOPPED
}

class Plugin implements Runnable {

    private PluginInterface pluginInterface;
    private Thread pluginLoopThread;
    private PLUGINSTATE state;

    public Plugin(PluginInterface pluginInterface) {
        this.pluginInterface = pluginInterface;
        startPlugin();
    }

    public void startPlugin() {
        if (pluginInterface.start()) { //plugin will return true if it has successfully started.. else false
            //if true -> start main loop from plugin
            System.out.println("Plugin '" + pluginInterface.Manifest().name + "' version " + pluginInterface.Manifest().version + " successfully loaded.");
            state = PLUGINSTATE.RUNNING;
            pluginLoopThread = new Thread(this);
            pluginLoopThread.start();
        }
    }

    public void pausePlugin() {
        if (pluginInterface.pause()) state = PLUGINSTATE.PAUSED;
    }

    public void resumePlugin() {
        pluginInterface.resume();
        state = PLUGINSTATE.RUNNING;
    }

    public void stopPlugin() {
        pluginInterface.stop();
        state = PLUGINSTATE.STOPPED;
    }

    public void sendEvent(EventHandler e, Map<String, String> eventData) {
        //TODO: add event handler support -> sent correct event to pluginInterface

        if (e.getEventType() == EventHandler.Event.SAY) {

            String playerName = eventData.get("playerName");
            String playerGUID = eventData.get("guid");
            String message = eventData.get("message");
            String time = eventData.get("time");

            Player p = new Player(playerName, playerGUID);

            pluginInterface.onPlayerSay(p, message, time);

        } else if (e.getEventType() == EventHandler.Event.DAMAGE) {

            String time = eventData.get("time");

            String victimguid = eventData.get("victim_guid");
            String victimname = eventData.get("victim_name");

            String attackerguid = eventData.get("attacker_guid");
            String attackername = eventData.get("attacker_name");

            String weapon = eventData.get("weapon");
            String weapon_bullet_type = eventData.get("weapon_bullet_type");
            String hitloc = eventData.get("hitlocation");

            Player victim = new Player(victimname, victimguid);
            Player attacker = new Player(attackername, attackerguid);

            pluginInterface.onPlayerDamage(attacker, victim, weapon, weapon_bullet_type, hitloc, time);

        } else if (e.getEventType() == EventHandler.Event.KILL) {

            String time = eventData.get("time");

            String victimguid = eventData.get("victim_guid");
            String victimname = eventData.get("victim_name");

            String attackerguid = eventData.get("attacker_guid");
            String attackername = eventData.get("attacker_name");

            String weapon = eventData.get("weapon");
            String weapon_bullet_type = eventData.get("weapon_bullet_type");
            String hitloc = eventData.get("hitlocation");

            Player victim = new Player(victimname, victimguid);
            Player attacker = new Player(attackername, attackerguid);

            pluginInterface.onPlayerKilled(attacker, victim, weapon, weapon_bullet_type, hitloc, time);

        } else if (e.getEventType() == EventHandler.Event.WEAPON) {

            String time = eventData.get("time");
            String playername = eventData.get("playerName");
            String playerguid = eventData.get("guid");
            String weapon = eventData.get("weapon");

            Player p = new Player(playername, playerguid);

            pluginInterface.onWeaponPickup(p, weapon, time);

        } else if (e.getEventType() == EventHandler.Event.QUIT) {

            String time = eventData.get("time");
            String playername = eventData.get("playerName");
            String playerguid = eventData.get("guid");

            Player p = new Player(playername, playerguid);

            pluginInterface.onPlayerQuit(p, time);

        } else if (e.getEventType() == EventHandler.Event.JOIN) {

            String time = eventData.get("time");
            String playername = eventData.get("playerName");
            String playerguid = eventData.get("guid");

            Player p = new Player(playername, playerguid);

            pluginInterface.onPlayerJoined(p, time);

        }
    }

    @Override
    public void run() {
        while (true) {
            if (state == PLUGINSTATE.RUNNING) {
                //The main loop of the plugin will run in this thread
                int wait = pluginInterface.loop(); //Loop will return the wait time for the next time we go through the loop
                try {
                    Thread.sleep(wait);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}