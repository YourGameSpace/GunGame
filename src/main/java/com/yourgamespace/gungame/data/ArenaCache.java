package com.yourgamespace.gungame.data;

import com.yourgamespace.gungame.manager.ArenaManager;

import java.util.HashMap;

public class ArenaCache {

    public ArenaCache() {}

    private final HashMap<String, ArenaManager> arenas = new HashMap<>();
    private final HashMap<Integer, String> arenaIds = new HashMap<>();

    public void addArena(String mapName, ArenaManager arenaManager) {
        arenas.put(mapName, arenaManager);
    }

    public void removeArena(String mapName) {
        arenas.remove(mapName);
    }

    public ArenaManager getArena(String mapName) {
        return arenas.getOrDefault(mapName, null);
    }

    public ArenaManager getArenaFromId(int arenaId) {
        return arenas.getOrDefault(arenaIds.get(arenaId), null);
    }

    public boolean isArenaExists(String mapName) {
        return arenas.containsKey(mapName);
    }

    public boolean isArenaIdExists(int arenaId) {
        return arenaIds.containsKey(arenaId);
    }

    public HashMap<String, ArenaManager> getArenas() {
        return arenas;
    }

    public HashMap<Integer, ArenaManager> getArenaIds() {
        return arenaIds;
    }
}
