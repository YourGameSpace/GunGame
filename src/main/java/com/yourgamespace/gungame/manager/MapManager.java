package com.yourgamespace.gungame.manager;

import org.bukkit.Location;

@SuppressWarnings("unused")
public class MapManager {

    private final String mapName;
    private final Location spawnLocation;
    private final int spawnProtectionRadius;

    public MapManager(String mapName, Location spawnLocation, int spawnProtectionRadius) {
        this.mapName = mapName;
        this.spawnLocation = spawnLocation;
        this.spawnProtectionRadius = spawnProtectionRadius;
    }

    public String getMapName() {
        return mapName;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public int getSpawnProtectionRadius() {
        return spawnProtectionRadius;
    }
}
