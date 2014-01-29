package com.deltabot.handlers.game.cod4;

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
		super.setIsEventHandled(true);
	}

}