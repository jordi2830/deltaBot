package com.deltabot.handlers.game.cod4.events;

import java.util.Map;

import com.deltabot.handlers.game.cod4.PluginHandler;


public class DamageEventHandler extends EventHandler {

    private Map<String, String> damageData;

    public DamageEventHandler(Event evt) {
        super(evt);
    }

    public void setData(Map<String, String> inData) {
        damageData = inData;
    }


    public void pushData() {
        //Pushdata to API
        PluginHandler.raiseEvent(this, damageData);
        super.setIsEventHandled(true);
    }

}