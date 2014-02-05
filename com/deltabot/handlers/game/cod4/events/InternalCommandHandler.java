package com.deltabot.handlers.game.cod4.events;

import com.deltabot.api.game.cod4.Player;
import com.deltabot.handlers.game.cod4.CommandHandler;

import java.util.Map;

public class InternalCommandHandler extends EventHandler {

    private Map<String, String> sayData;

    public InternalCommandHandler(Event evt) {
        super(evt);
    }

    public void setData(Map<String, String> inData) {
        sayData = inData;
    }

    public void pushData() {
        //We don't need to push this to the api, as we don't want plugins to access these commands

        CommandHandler.executeCommand(sayData.get("message").substring(1, sayData.get("message").length()), new Player(sayData.get("playerName"), sayData.get("guid")));
        super.setIsEventHandled(true);
    }

}