package com.deltabot.handlers.game.cod4;

import java.util.Map;


public class QuitEventHandler extends EventHandler {

	private Map<String, String> quitData;
	
	public QuitEventHandler(Event evt) {
		super(evt);
	}
	
	public void setData(Map<String, String> inData){
		quitData = inData;
	}
	
	public void pushData(){
		//Pushdata to API
		super.setIsEventHandled(true);
	}

}