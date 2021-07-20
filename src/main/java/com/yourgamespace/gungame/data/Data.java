package com.yourgamespace.gungame.data;

import com.yourgamespace.gungame.manager.ArenaManager;
import com.yourgamespace.gungame.utils.MapCreator;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class Data {

    public Data() {}

    private final HashMap<Integer, ArenaManager> arenas = new HashMap<>();
    private final HashMap<UUID, MapCreator> mapCreators = new HashMap<>();

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

    //START: MapCreator
    public void addMapCreator(Player player, MapCreator mapCreator) {
        mapCreators.put(player.getUniqueId(), mapCreator);
    }

    public void removeMapCreator(Player player) {
        mapCreators.remove(player.getUniqueId());
    }

    public MapCreator getMapCreator(Player player) {
        return mapCreators.getOrDefault(player.getUniqueId(), new MapCreator(player));
    }

    public boolean isPlayerInMapCreation(Player player) {
        return mapCreators.containsKey(player.getUniqueId());
    }
    //EMD: MapCreator

}
