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
	
	public Map<String, String> getData(){
		return quitData;
	}

}