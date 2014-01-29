package com.deltabot.handlers.game.cod4;

import java.util.Map;


public class SayEventHandler extends EventHandler {

	private Map<String, String> sayData;
	
	public SayEventHandler(Event evt) {
		super(evt);
	}
	
	public void setData(Map<String, String> inData){
		sayData = inData;
	}
	
	public Map<String, String> getData(){
		return sayData;
	}

}