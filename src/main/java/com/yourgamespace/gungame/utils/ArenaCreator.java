package com.yourgamespace.gungame.utils;

import org.bukkit.entity.Player;

import java.util.UUID;

@SuppressWarnings("unused")
public class ArenaCreator {

    private final UUID ownerUuid;
    private final String ownerName;
    private int step = 1;

    private String arenaName;
    private String mapName;

    public ArenaCreator(Player player) {
        this.ownerUuid = player.getUniqueId();
        this.ownerName = player.getName();
    }

    public int getCurrentStep() {
        return step;
    }

    public void addStep() {
        step++;
    }

    public UUID getOwnerUuid() {
        return ownerUuid;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setArenaName(String arenaName) {
        this.arenaName = arenaName;
    }

    public String getArenaName() {
        return arenaName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public String getMapName() {
        return mapName;
    }

}
