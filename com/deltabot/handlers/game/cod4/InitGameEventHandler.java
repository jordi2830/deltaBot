package com.deltabot.handlers.game.cod4;

import java.util.Map;

import com.humborstad.test_java.EventHandler;
import com.humborstad.test_java.EventHandler.Event;


public class InitGameEventHandler extends EventHandler {

	private Map<String, String> initGameData;
	
	public InitGameEventHandler(Event evt) {
		super(evt);
	}
	
	public void setData(Map<String, String> inData){
		initGameData = inData;
	}
	
	public Map<String, String> getData(){
		return initGameData;
	}

}