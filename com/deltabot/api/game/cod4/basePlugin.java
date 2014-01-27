package com.deltabot.api.game.cod4;

import com.deltabot.api.Manifest;

public interface BasePlugin {

    public boolean start(); //if true is returned -> plugin has succesfully started
    public void stop(); //called when a plugin is stopped by an admin
    public boolean pause(); //called when a plugin is paused by an admin -> if true is returned -> plugin is paused

    public int loop(); //main loop of the plugin. Returned integer = time to wait before next time we start the loop

    public void onPlayerJoined(Player player);
    public void onPlayerLeft(Player player);
    public void onPlayerKilled(Player killer, Player victim);
    public void onPlayerSuicide(Player victim);
    public void onWeaponPickup(Player player, String weaponName);
    public void onPlayerSay(Player player, String message);

    public Manifest Manifest();

}
