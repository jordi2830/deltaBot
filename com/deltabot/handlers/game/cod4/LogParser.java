package com.deltabot.handlers.game.cod4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.deltabot.handlers.game.cod4.EventHandler.Event;

 
public class LogParser {
	
	public void loadNewEventElements(String fileLocation) throws IOException{
		
		ArrayList<EventHandler> logLines = new ArrayList<EventHandler>();
		
		File logFile = new File(fileLocation);
		Scanner in = new Scanner(logFile);
		LogReader read = new LogReader();
		
		
		//Possible FIXME: There might be a one-off error here.
		//skipping through the file
		int i = 0;
		while(i < read.getLineCount() && in.hasNextLine()){
			in.nextLine();
			i++;
		}
		
		while(in.hasNextLine()){
			String k = in.nextLine();
			EventHandler evt = formatString(k);
			if(!evt.getIsEventHandled()){
				logLines.add(evt);
			}
		}
		in.close();
		read.updateLineCount();
		pushEventsToAPI(logLines);
	}
	
	private void pushEventsToAPI(ArrayList<EventHandler> events){
		for(EventHandler iter : events){
			iter.pushData();
		}
	}
	
	private EventHandler formatString(String inString){
		EventHandler stringArr = new EventHandler(Event.NONE);
		
		if(inString.contains("InitGame:")){
			stringArr = formatInitGame(inString);;
		} else if(inString.split(";")[0].endsWith("J")){
			stringArr = formatJoin(inString);
		} else if(inString.split(";")[0].endsWith("Q")){
			stringArr = formatQuit(inString);
		} else if(inString.split(";")[0].endsWith("K")){
			stringArr = formatKill(inString);
		} else if(inString.split(";")[0].endsWith("say")){
			stringArr = formatSay(inString);
		}
		return stringArr;
		
		
	}
	
	private EventHandler formatInitGame(String inString){		
		//First element is header, we don't want that.
		//String[] slicedData = Arrays.copyOfRange(inString., 6, inString.length());
		
		//Lets first handle the serverInfo, which is the first element of slicedData.
		//Lets split the serverinfo key-valuepairs into an array
		
		Pattern p = Pattern.compile("\\", Pattern.LITERAL);
		String[] keyvalue = p.split(inString);
		
		HashMap<String, String> serverInfo = new HashMap<String, String>();
		for(int i = 1; i < keyvalue.length; i += 2){
			if(i + 1 < keyvalue.length){
				String key = keyvalue[i];
				String value = keyvalue[i + 1];
				//System.out.println(key + " => "+ value);
				serverInfo.put(key, value);
			}
		}
		EventHandler k = new InitGameEventHandler(Event.INIT_GAME);
		k.setData(serverInfo);
		return k;	
	}
	
	private EventHandler formatJoin(String inString){
		String[] playerDataArray = inString.split(";");
		//  8:03 Q;d3d1442eed3e6d4dc24246c00a050914;0;[D.R] SnAg
		Map<String, String> m = new HashMap<String, String>();
		
		m.put("guid", playerDataArray[1]);
		m.put("playerName", playerDataArray[3]);
		
		EventHandler k = new JoinEventHandler(Event.JOIN);
		k.setData(m);
		
		return k;
	}
	
	private EventHandler formatQuit(String inString){
		String[] playerDataArray = inString.split(";");
		//  8:03 Q;d3d1442eed3e6d4dc24246c00a050914;0;[D.R] SnAg
		Map<String, String> m = new HashMap<String, String>();
		
		m.put("guid", playerDataArray[1]);
		m.put("playerName", playerDataArray[3]);
		
		EventHandler k = new QuitEventHandler(Event.QUIT);
		k.setData(m);
		
		return k;
	}
	
	private EventHandler formatSay(String inString){
		//142:38 say;d3d1442eed3e6d4dc24246c00a050914;0;[D.R] SnAg;QUICKMESSAGE_YES_SIR
		String[] sayData = inString.split(";");
		Map<String, String> k = new HashMap<String, String>();
		k.put("guid", sayData[1]);
		k.put("playerName", sayData[3]);
		k.put("message", sayData[4].substring(1));
		EventHandler i = new SayEventHandler(Event.SAY);
		i.setData(k);
		return i;
	}
	
	private EventHandler formatKill(String inString){
		String[] dataArray = inString.split(";");
		
		// 65:14 K;78f9bf2bd82e444b19e09585540aded9;0;;cx.4;d3d1442eed3e6d4dc24246c00a050914;1;;[D.R] SnAg;m4_gl_mp;30;MOD_RIFLE_BULLET;torso_lower
		// 109:10 K;d3d1442eed3e6d4dc24246c00a050914;0;;[D.R] SnAg;d3d1442eed3e6d4dc24246c00a050914;-1;;[D.R] SnAg;frag_grenade_mp;367;MOD_GRENADE_SPLASH;none
		//  4:48 K;d3d1442eed3e6d4dc24246c00a050914;0;;[D.R] SnAg;;-1;world;;none;14;MOD_FALLING;none
		Map<String, String> info = new HashMap<String, String>();

		info.put("attacker_guid", dataArray[1]);
		info.put("attacker_name", dataArray[4]);
		info.put("victim_guid", dataArray[5]);
		info.put("victim_name", dataArray[8]);
		info.put("weapon", dataArray[9]);
		info.put("weapon_bullet_type", dataArray[11]);
		info.put("hitlocation", dataArray[12]);
		
		//System.out.println(inString);
		
		EventHandler k = new KillEventHandler(Event.KILL);
		k.setData(info);
		return k;
	}
}