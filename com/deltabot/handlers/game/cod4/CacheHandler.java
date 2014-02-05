package com.deltabot.handlers.game.cod4;

import com.deltabot.api.game.cod4.Functions;
import com.deltabot.api.game.cod4.Player;

import java.util.ArrayList;
import java.util.List;

public class CacheHandler {

    //Usage of this class: to store player objects for later purposes (check if they are already in our server when a join event is raised)

    private static List<Player> currentPlayers = new ArrayList<Player>();

    public static void init() {
        //Copy the current players to our cache
        currentPlayers.addAll(Functions.getCurrentPlayers());
    }

    public static boolean isPlayerInServer(String GUID) {

        if (currentPlayers.size() == 0) {

        }

        for (Player p : currentPlayers) {
            if (p.GUID.equals(GUID)) return true;
        }

        return false;
    }

    public static void addPlayerToPlayerListCache(Player p) {
        currentPlayers.add(p);
    }

    public static void removePlayerFromPlayerListCache(Player p) {
        for (Player pl : currentPlayers) {
            if (pl.GUID.equals(p.GUID)) {
                currentPlayers.remove(pl);
                break;
            }
        }
    }
}
