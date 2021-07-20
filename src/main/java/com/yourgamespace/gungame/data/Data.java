package com.yourgamespace.gungame.data;

import com.yourgamespace.gungame.manager.ArenaManager;

import java.util.HashMap;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class Data {

    public Data() {}

    private final Integer currentConfigVersion = 1;
    private final String mapConfigPath = "plugins/GunGame/Maps/MapConfigs";
    private final String mapStoragePath = "plugins/GunGame/Maps/MapStorage";
    private final String arenaConfigPath = "plugins/GunGame/Arenas";

    private final HashMap<Integer, ArenaManager> arenas = new HashMap<>();

    private boolean bungeeCord = true;
    private boolean protocollib = true;

    //START: Static Values
    public int getCurrentConfigVersion() {
        return currentConfigVersion;
    }

    public String getMapConfigPath() {
        return mapConfigPath;
    }

    public String getMapStoragePath() {
        return mapStoragePath;
    }

    public String getArenaConfigPath() {
        return arenaConfigPath;
    }

    //END: Static Values

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
