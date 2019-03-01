package com.deltabot.handlers;

import java.io.IOException;
import java.util.Arrays;

public class RCONHandler {
    private static final int NETWORK_TIMEOUT = 1000;
    private static DatagramPacket packet;
    private static DatagramPacket receivedPacket;
    private static DatagramSocket dsocket;

    static {
        try {
            dsocket = new DatagramSocket();
            dsocket.setSoTimeout(NETWORK_TIMEOUT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static String sendRCON(String message) {

        String IP = VariableHandler.getVariableValue("server_ip");
        int aPort = Integer.valueOf(VariableHandler.getVariableValue("server_port"));
        String password = VariableHandler.getVariableValue("rcon_password");

        String formattedMessage = "rcon \"" + password + "\" " + message;

        try {
            constructPacket(IP, aPort, formattedMessage);
            sendPacket();
            return new String(receivePacket());
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String sendGetStatus() {

        String IP = VariableHandler.getVariableValue("server_ip");
        int aPort = Integer.valueOf(VariableHandler.getVariableValue("server_port"));

        String formattedMessage = "getstatus";

        try {
            constructPacket(IP, aPort, formattedMessage);
            sendPacket();
            return new String(receivePacket());
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void constructPacket(String anAddress, int aPort, String message) throws SocketException, UnknownHostException {
        byte[] sendBuf = new byte[65507];

        sendBuf = ("xxxx" + message + "\n").getBytes();

        //Our packets need a header with these bytes
        //to conform with the query protocol used by Quake 3.
        sendBuf[0] = (byte) 0xff;
        sendBuf[1] = (byte) 0xff;
        sendBuf[2] = (byte) 0xff;
        sendBuf[3] = (byte) 0xff;

        InetAddress address = InetAddress.getByName(anAddress);
        int port = aPort;

        packet = new DatagramPacket(sendBuf, sendBuf.length,
                address, port);
    }

    private static void sendPacket() throws IOException {
        dsocket.send(packet);
    }

    private static byte[] receivePacket() throws IOException {
        byte[] receiveBuf = new byte[65507];
        receivedPacket = new DatagramPacket(receiveBuf, receiveBuf.length);
        try {
            dsocket.receive(receivedPacket);
        } catch (SocketTimeoutException e) {
            //Sometimes this fails, I'm not quite sure yet why, but investigation is needed
        }
        byte[] received = Arrays.copyOf(receivedPacket.getData(), receivedPacket.getLength());
        try {
            Thread.sleep(500); //Sleep because if we do too many rcon requests at a short time they will time out.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return received;
    }
}

