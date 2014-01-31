package com.deltabot.handlers.game.cod4.events;

import com.deltabot.handlers.game.cod4.PluginHandler;

import java.util.Map;

public class ExitLevelEventHandler extends EventHandler {

    private Map<String, String> exitLevelData;

    public ExitLevelEventHandler(Event evt) {
        super(evt);
    }

    public void setData(Map<String, String> inData) {
        exitLevelData = inData;
    }

    public void pushData() {
        //Pushdata to API
        PluginHandler.raiseEvent(this, exitLevelData);
        super.setIsEventHandled(true);
    }

}