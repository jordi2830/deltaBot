package com.deltabot.handlers.game.cod4;

import java.util.Map;

public class EventHandler {
	
	public enum Event {
	    INIT_GAME, JOIN, QUIT, KILL, SAY, NONE
	}
	
	private Event eventType;
	private boolean isEventHandled;
	
	public EventHandler(Event evt){
		eventType = evt;
	}
	
	public Event getEventType(){
		return eventType;
	}
	
	public void setEventType(Event inEvent){
		eventType = inEvent;
	}
	
	public boolean getIsEventHandled(){
		return isEventHandled;
	}
	
	public void setIsEventHandled(boolean inBool){
		isEventHandled = inBool;
	}
	
	public void setData(Map<String, String> inData){
		
	}
	
	public void pushData(){
		//Should be overridden in each specific class so that it's date is
		//sent to the API

		
	}
	
}
