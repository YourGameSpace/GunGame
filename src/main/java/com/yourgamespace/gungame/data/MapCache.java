package com.yourgamespace.gungame.data;

import com.yourgamespace.gungame.manager.MapManager;

import java.util.HashMap;

public class MapCache {

    public MapCache() {}

    private final HashMap<String, MapManager> maps = new HashMap<>();

    public void addMap(String mapName, MapManager mapManager) {
        maps.put(mapName, mapManager);
    }

    public void removeMap(String mapName) {
        maps.remove(mapName);
    }

    public MapManager getMap(String mapName) {
        return maps.getOrDefault(mapName, null);
    }

    public boolean isMapExists(String mapName) {
        return maps.containsKey(mapName);
    }

    public HashMap<String, MapManager> getMaps() {
        return maps;
    }
}
