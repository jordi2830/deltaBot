package com.deltabot.handlers.game.cod4;
import java.util.Map;


public class KillEventHandler extends EventHandler {

	private Map<String, String> killData;
	
	public KillEventHandler(Event evt) {
		super(evt);
	}
	
	public void setData(Map<String, String> inData){
		killData = inData;
	}
	

	public void pushData(){
		//Pushdata to API
		super.setIsEventHandled(true);
	}

}
