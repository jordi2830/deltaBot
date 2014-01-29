package com.deltabot.handlers.game.cod4;

import com.deltabot.handlers.game.cod4.PluginHandler;

import java.util.Map;


public class InitGameEventHandler extends EventHandler {

	private Map<String, String> initGameData;
	
	public InitGameEventHandler(Event evt) {
		super(evt);
	}
	
	public void setData(Map<String, String> inData){
		initGameData = inData;
	}
	
	public void pushData(){
		//Pushdata to API
        PluginHandler.raiseEvent(this, initGameData);
		super.setIsEventHandled(true);
	}

}