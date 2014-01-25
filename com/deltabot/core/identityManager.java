package com.deltabot.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class identityManager {

    public static boolean verifyKey(String key){

        //TODO: 1) verify if key is existent (server sided php script?)
        //      2) verify if the user bound to this key is not banned (also server sided php script?)

        try {
            URL url = new URL("http://" + settings.masterServerIP + ":" + settings.masterServerPort + "/verifykey.php?key=" + key);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String response = reader.readLine(); //Response should only be 1 line, so no need to read more

            if (response.startsWith("valid")){
                return true;

            } else if (response.startsWith("invalid")){
                // either "valid" or "invalid;reason"
                String reason = response.split(";")[1];
                return false;
            } else {
                //Some error has occured
                System.out.println("Unable to get a response from the server.");

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
        }
}
