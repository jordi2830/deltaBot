package com.deltabot.handlers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Quake3Handler {
	private final int NETWORK_TIMEOUT = 500;
	private DatagramPacket packet;
	private DatagramPacket receivedPacket;
	private DatagramSocket dsocket;
	private byte[] sendBuf = new byte[65507];
	private byte[] receiveBuf = new byte[65507];
	
	public Quake3Handler() throws SocketException{
		dsocket = new DatagramSocket();
		dsocket.setSoTimeout(NETWORK_TIMEOUT);
	}
	
	public String sendRCON(String IP, int aPort, String password, String message) throws IOException{
		
		String formattedMessage = "rcon \"" + password + "\" " + message;
		
		constructPacket(IP, aPort, formattedMessage);
		sendPacket();
		return receivePacket().toString();
		
	}
	
	public String sendGetStatus(String IP, int aPort) throws IOException{
		
		String formattedMessage = "getstatus";
		
		constructPacket(IP, aPort, formattedMessage);
		sendPacket();
		return receivePacket().toString();
		
	}
	
	private void constructPacket(String anAddress, int aPort, String message) throws SocketException, UnknownHostException{
		sendBuf = ("xxxx" + message + "\n").getBytes();
		
		//Our packets need a header with these bytes
		//to conform with the query protocol used by Quake 3.
		sendBuf[0] = (byte)0xff;
		sendBuf[1] = (byte)0xff;
		sendBuf[2] = (byte)0xff;
		sendBuf[3] = (byte)0xff;
		
		InetAddress address = InetAddress.getByName(anAddress);
		int port = aPort;
		
		packet = new DatagramPacket(sendBuf, sendBuf.length, 
		                                address, port);
	}
	
	private void sendPacket() throws IOException{
		dsocket.send(packet);
	}
	
	private byte[] receivePacket() throws IOException{
		receivedPacket = new DatagramPacket(receiveBuf, receiveBuf.length);
		dsocket.receive(receivedPacket);
		byte[] received = Arrays.copyOf(receivedPacket.getData(), receivedPacket.getLength());
		return received;
	}
}

