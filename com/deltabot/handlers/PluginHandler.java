package com.deltabot.handlers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PluginHandler {

    private static List<Plugin> loadedPlugins = new ArrayList<Plugin>();

    public static void loadPlugin(){

    }

    private static void pluginStart(){

    }

    public static void pluginStop(){

    }

    public static void pluginPause(){

    }

}

class Plugin{

}

class PluginLoader extends ClassLoader{

    File pluginFile;

    public PluginLoader (File file) {
        this.pluginFile = file;
    }

    public Class loadClass (String name) throws ClassNotFoundException {
        return loadClass(name, true);
    }

}
