package com.deltabot.handlers.game.cod4.events;

import com.deltabot.handlers.game.cod4.PluginHandler;

import java.util.Map;

public class ShutdownGameEventHandler extends EventHandler {

    private Map<String, String> shutdownGameData;

    public ShutdownGameEventHandler(Event evt) {
        super(evt);
    }

    public void setData(Map<String, String> inData) {
        shutdownGameData = inData;
    }

    public void pushData() {
        //Pushdata to API
        PluginHandler.raiseEvent(this, shutdownGameData);
        super.setIsEventHandled(true);
    }

}