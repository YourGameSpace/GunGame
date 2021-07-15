package com.yourgamespace.gungame.data;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class Data {

    public Data() {}

    private final Integer currentConfigVersion = 1;
    private boolean bungeeCord = true;

    public int getCurrentConfigVersion() {
        return currentConfigVersion;
    }

    public boolean isServerModeBungeeCord() {
        return bungeeCord;
    }

    public void setBungeeCord(boolean bungeeCord) {
        this.bungeeCord = bungeeCord;
    }
}
