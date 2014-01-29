package com.deltabot.handlers.game.cod4;

import com.deltabot.api.game.cod4.BasePlugin;
import com.deltabot.api.game.cod4.Functions;
import com.deltabot.api.game.cod4.Player;
import sun.org.mozilla.javascript.internal.Function;

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

        if(e.getEventType() == EventHandler.Event.SAY){
            String playerName = eventData.get("playerName");
            String message = eventData.get("message");

            Player p = Functions.getPlayerByName(playerName);

            basePlugin.onPlayerSay(p, message);
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