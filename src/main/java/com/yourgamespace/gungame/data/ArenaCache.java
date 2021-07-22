package com.yourgamespace.gungame.data;

import com.yourgamespace.gungame.manager.ArenaManager;

import java.util.HashMap;

public class ArenaCache {

    public ArenaCache() {}

    private final HashMap<String, ArenaManager> arenas = new HashMap<>();

    public void addArena(String mapName, ArenaManager arenaManager) {
        arenas.put(mapName, arenaManager);
    }

    public void removeArena(String mapName) {
        arenas.remove(mapName);
    }

    public ArenaManager getArena(String mapName) {
        return arenas.getOrDefault(mapName, null);
    }

    public boolean isArenaExists(String mapName) {
        return arenas.containsKey(mapName);
    }

    public HashMap<String, ArenaManager> getArenas() {
        return arenas;
    }
}
