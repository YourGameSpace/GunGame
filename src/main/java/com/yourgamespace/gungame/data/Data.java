package com.yourgamespace.gungame.data;

import com.yourgamespace.gungame.manager.ArenaManager;

import java.util.HashMap;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class Data {

    public Data() {}

    private final HashMap<Integer, ArenaManager> arenas = new HashMap<>();

    private final Integer currentConfigVersion = 1;
    private boolean bungeeCord = true;
    private boolean protocollib = true;

    public int getCurrentConfigVersion() {
        return currentConfigVersion;
    }

    public boolean isServerModeBungeeCord() {
        return bungeeCord;
    }

    public void setBungeeCord(boolean bungeeCord) {
        this.bungeeCord = bungeeCord;
    }

    public boolean isProtocollibInstalled() {
        return protocollib;
    }

    public void setProtocollib(boolean protocollib) {
        this.protocollib = protocollib;
    }

    //START: Arenas
    public void addArena(ArenaManager arenaManager) {
        arenas.put(arenaManager.getArenaId(), arenaManager);
    }

    public void removeArena(int arenaId) {
        arenas.remove(arenaId);
    }

    public ArenaManager getArena(int arenaId) {
        return arenas.get(arenaId);
    }
    //END: Arenas

}
