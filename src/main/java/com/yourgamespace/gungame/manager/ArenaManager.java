package com.yourgamespace.gungame.manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

@SuppressWarnings("unused")
public class ArenaManager {

    private boolean isEnabled;
    private final int arenaId;
    private final String arenaName;
    private final MapManager mapManager;
    private final int maxPlayers;
    private final ArrayList<UUID> players = new ArrayList<>();

    public ArenaManager(int arenaId, String arenaName, MapManager mapManager, int maxPlayers) {
        this.isEnabled = false;
        this.arenaId = arenaId;
        this.arenaName = arenaName;
        this.mapManager = mapManager;
        this.maxPlayers = maxPlayers;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void enableArena() {
        //DO STUFF

        isEnabled = true;
    }

    public void disableArena() {
        isEnabled = false;

        //DO STUFF
    }

    public int getArenaId() {
        return arenaId;
    }

    public String getArenaName() {
        return arenaName;
    }

    public MapManager getMapManager() {
        return mapManager;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void handlePlayerJoin(Player player) {
        addPlayer(player.getUniqueId());

        //DO STUFF
    }

    private void addPlayer(UUID uuid) {
        players.add(uuid);
    }

    public void handlePlayerLeave(Player player) {
        //DO STUFF

        removePlayer(player.getUniqueId());
    }

    private void removePlayer(UUID uuid) {
        players.remove(uuid);
    }

    public int getCurrentPlayers() {
        return (players.size() + 1);
    }
}
