package com.deltabot.api;

public class Manifest {

    public String author;
    public String name;
    public String description;
    public int version;
    public SUPPORTEDGAME[] supportedGames;

    public Manifest(String author, String name, String description, int version, SUPPORTEDGAME[] supportedGames) {
        this.author = author;
        this.name = name;
        this.description = description;
        this.version = version;
        this.supportedGames = supportedGames;
    }
}

