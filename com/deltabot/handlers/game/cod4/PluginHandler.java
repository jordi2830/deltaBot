package com.deltabot.handlers.game.cod4;

import com.deltabot.api.game.cod4.BasePlugin;
import com.deltabot.api.game.cod4.Functions;
import com.deltabot.api.game.cod4.Player;
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
        BasePlugin newPlugin = (BasePlugin) cl.loadClass(pluginMainClass).newInstance();

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

    private BasePlugin basePlugin;
    private Thread pluginLoopThread;
    private PLUGINSTATE state;

    public Plugin(BasePlugin basePlugin) {
        this.basePlugin = basePlugin;
        startPlugin();
    }

    public void startPlugin() {
        if (basePlugin.start()) { //plugin will return true if it has successfully started.. else false
            //if true -> start main loop from plugin
            System.out.println("Plugin '" + basePlugin.Manifest().name + "' version " + basePlugin.Manifest().version + " successfully loaded.");
            state = PLUGINSTATE.RUNNING;
            pluginLoopThread = new Thread(this);
            pluginLoopThread.start();
        }
    }

    public void pausePlugin() {
        if (basePlugin.pause()) state = PLUGINSTATE.PAUSED;
    }

    public void resumePlugin() {
        basePlugin.resume();
        state = PLUGINSTATE.RUNNING;
    }

    public void stopPlugin() {
        basePlugin.stop();
        state = PLUGINSTATE.STOPPED;
    }

    public void sendEvent(EventHandler e, Map<String, String> eventData) {
        //TODO: add event handler support -> sent correct event to basePlugin

        if (e.getEventType() == EventHandler.Event.SAY) {

            String playerName = eventData.get("playerName");
            String message = eventData.get("message");
            String time = eventData.get("time");

            Player p = Functions.getPlayerByName(playerName);

            basePlugin.onPlayerSay(p, message, time);

        } else if (e.getEventType() == EventHandler.Event.DAMAGE) {

            String time = eventData.get("time");
            String victimguid = eventData.get("victim_guid");
            String attackerguid = eventData.get("attacker_guid");
            String weapon = eventData.get("weapon");
            String weapon_bullet_type = eventData.get("weapon_bullet_type");
            String hitloc = eventData.get("hitlocation");

            Player victim = Functions.getPlayerByGUID(victimguid);
            Player attacker = Functions.getPlayerByGUID(attackerguid);

            basePlugin.onPlayerDamage(attacker, victim, weapon, weapon_bullet_type, hitloc, time);
        } else if (e.getEventType() == EventHandler.Event.KILL) {

            String time = eventData.get("time");
            String victimguid = eventData.get("victim_guid");
            String attackerguid = eventData.get("attacker_guid");
            String weapon = eventData.get("weapon");
            String weapon_bullet_type = eventData.get("weapon_bullet_type");
            String hitloc = eventData.get("hitlocation");

            Player victim = Functions.getPlayerByGUID(victimguid); //TODO: find a way to bypass the need to get a status command out each time we get a player. (possibly cache data? and only fetch the new data through status when ping or score is requested by a plugin?) -> CacheHandler
            Player attacker = Functions.getPlayerByGUID(attackerguid);

            basePlugin.onPlayerKilled(attacker, victim, weapon, weapon_bullet_type, hitloc, time);
        }
    }

    @Override
    public void run() {
        while (true) {
            if (state == PLUGINSTATE.RUNNING) {
                //The main loop of the plugin will run in this thread
                int wait = basePlugin.loop(); //Loop will return the wait time for the next time we go through the loop
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