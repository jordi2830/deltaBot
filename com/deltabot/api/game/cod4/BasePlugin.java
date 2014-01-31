package com.deltabot.api.game.cod4;

import com.deltabot.api.Manifest;

public interface BasePlugin {

    public boolean start(); //if true is returned -> plugin has succesfully started

    public void stop(); //called when a plugin is stopped by an admin

    public boolean pause(); //called when a plugin is paused by an admin -> if true is returned -> plugin is paused

    public void resume(); //called when a plugin was paused, but is now resumed

    public int loop(); //main loop of the plugin. Returned integer = time to wait before next time we start the loop

    public void onPlayerJoined(Player player, String time);

    public void onPlayerLeft(Player player, String time);

    public void onPlayerDamage(Player attacker, Player victim, String weapon, String weapon_bullet_type, String hitLoc, String time);

    public void onPlayerBanned(Player player, String reason, String duration, String caller, String time);

    public void onPlayerKilled(Player attacker, Player victim, String weapon, String weapon_bullet_type, String hitLoc, String time);

    public void onPlayerSuicide(Player victim, String time);

    public void onWeaponPickup(Player player, String weaponName, String time);

    public void onPlayerSay(Player player, String message, String time);

    public void onMapChange(String oldMap, String newMap);

    public Manifest Manifest();

}
