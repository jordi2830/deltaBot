package com.deltabot.handlers.game.cod4.events;

import java.util.Map;

import com.deltabot.handlers.game.cod4.PluginHandler;

public class InitGameEventHandler extends EventHandler {

    private Map<String, String> initGameData;

    public InitGameEventHandler(Event evt) {
        super(evt);
    }

    public void setData(Map<String, String> inData) {
        initGameData = inData;
    }

    public void pushData() {
        //Pushdata to API
        PluginHandler.raiseEvent(this, initGameData);
        super.setIsEventHandled(true);
    }

}