package com.deltabot.handlers.game.cod4.events;

import com.deltabot.handlers.game.cod4.PluginHandler;

import java.util.Map;

public class WeaponEventHandler extends EventHandler {

    private Map<String, String> weaponData;

    public WeaponEventHandler(Event evt) {
        super(evt);
    }

    public void setData(Map<String, String> inData) {
        weaponData = inData;
    }

    public void pushData() {
        //Pushdata to API
        PluginHandler.raiseEvent(this, weaponData);
        super.setIsEventHandled(true);
    }

}