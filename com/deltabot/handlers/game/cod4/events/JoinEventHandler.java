package com.deltabot.handlers.game.cod4.events;

import java.util.Map;

import com.deltabot.handlers.game.cod4.PluginHandler;

public class JoinEventHandler extends EventHandler {

    private Map<String, String> joinData;

    public JoinEventHandler(Event evt) {
        super(evt);
    }

    public void setData(Map<String, String> inData) {
        joinData = inData;
    }

    public void pushData() {
        PluginHandler.raiseEvent(this, joinData);
        super.setIsEventHandled(true);
    }

}