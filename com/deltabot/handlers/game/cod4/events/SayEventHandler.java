package com.deltabot.handlers.game.cod4.events;

import com.deltabot.handlers.game.cod4.PluginHandler;

import java.util.Map;

public class SayEventHandler extends EventHandler {

    private Map<String, String> sayData;

    public SayEventHandler(Event evt) {
        super(evt);
    }

    public void setData(Map<String, String> inData) {
        sayData = inData;
    }

    public void pushData() {
        //Pushdata to API
        PluginHandler.raiseEvent(this, sayData);
        super.setIsEventHandled(true);
    }

}