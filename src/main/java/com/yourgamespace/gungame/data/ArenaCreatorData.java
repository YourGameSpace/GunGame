package com.yourgamespace.gungame.data;

import com.yourgamespace.gungame.utils.ArenaCreator;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ArenaCreatorData {

    private static final HashMap<UUID, ArenaCreator> arenaCreators = new HashMap<>();

    public static void addArenaCreator(Player player, ArenaCreator arenaCreator) {
        arenaCreators.put(player.getUniqueId(), arenaCreator);
    }

    public static  void removeArenaCreator(Player player) {
        arenaCreators.remove(player.getUniqueId());
    }

    public static ArenaCreator getArenaCreator(Player player) {
        return arenaCreators.get(player.getUniqueId());
    }

    public static boolean isPlayerInArenaCreation(Player player) {
        return arenaCreators.containsKey(player.getUniqueId());
    }

}
