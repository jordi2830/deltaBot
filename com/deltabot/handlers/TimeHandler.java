package com.deltabot.handlers;

public class TimeHandler {

    private static TimeKeeper TK = new TimeKeeper();
    private static Thread uptimeKeeper = new Thread(TK);

    public static int uptime() {
        return TK.uptime;
    }

    ;

    public static void init() {
        uptimeKeeper.start();
    }
}

class TimeKeeper implements Runnable {

    public int uptime = 0;

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                uptime++;
            } catch (InterruptedException e) {

            }
        }

    }
}
