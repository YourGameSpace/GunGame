package com.yourgamespace.gungame.data;

import com.yourgamespace.gungame.manager.ArenaManager;

import java.util.HashMap;

@SuppressWarnings("ALL")
public class ArenaCache {

    public ArenaCache() {}

    private final HashMap<String, ArenaManager> arenas = new HashMap<>();
    private final HashMap<Integer, String> arenaIds = new HashMap<>();

    public void addArena(String arenaName, ArenaManager arenaManager) {
        arenas.put(arenaName, arenaManager);
    }

    public void removeArena(String arenaName) {
        arenas.remove(arenaName);
    }

    public ArenaManager getArena(String arenaName) {
        return arenas.getOrDefault(arenaName, null);
    }

    public ArenaManager getArenaFromId(int arenaId) {
        return arenas.getOrDefault(arenaIds.get(arenaId), null);
    }

    public boolean isArenaExists(String arenaName) {
        return arenas.containsKey(arenaName);
    }

    public boolean isArenaIdExists(int arenaId) {
        return arenaIds.containsKey(arenaId);
    }

    public HashMap<String, ArenaManager> getArenas() {
        return arenas;
    }

    public HashMap<Integer, String> getArenaIds() {
        return arenaIds;
    }
}
