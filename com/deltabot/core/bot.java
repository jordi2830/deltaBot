package com.deltabot.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class bot {

    public settings settings;

    public void init() {

        if (isValidKey(settings.identKey)) {
            System.out.println("The key '" + settings.identKey + "' is valid!");
        } else {
            System.out.println("The key '" + settings.identKey + "' is not valid!");
        }

    }

    private boolean isValidKey(String key) {

        try {
            URL url = new URL("http://" + "localhost" + ":" + "1337" + "/verifykey.php?key=" + key);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String response = reader.readLine(); //Response should only be 1 line, so no need to read more

            if (response.equals("valid")){
                return true;
            } else if (response.equals("invalid")){
                return false;
            } else {
                //Some other response (server error?) should be handled as invalid aswell
                return false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
