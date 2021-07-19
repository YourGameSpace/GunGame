package com.yourgamespace.gungame.manager;

import com.yourgamespace.gungame.data.Data;
import com.yourgamespace.gungame.main.GunGame;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("unused")
public class PlayerManager {

    private final Data data = GunGame.getData();

    private final HashMap<UUID, Integer> players = new HashMap<>();

    public PlayerManager() {}

    public void joinArena(Player player, int arenaId) {
        UUID uuid = player.getUniqueId();

        players.put(player.getUniqueId(), arenaId);
        ArenaManager arenaManager = data.getArena(arenaId);
        arenaManager.handlePlayerJoin(player);
    }

    public void leaveArena(Player player) {
        UUID uuid = player.getUniqueId();
        int arenaId = players.get(uuid);

        ArenaManager arenaManager = data.getArena(arenaId);
        arenaManager.handlePlayerLeave(player);
        players.remove(uuid);
    }

    public boolean isPlayerInArena(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    public int getArena(Player player) {
        return players.getOrDefault(player.getUniqueId(), null);
    }
}
