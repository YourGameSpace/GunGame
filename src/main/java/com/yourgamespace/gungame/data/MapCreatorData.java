package com.yourgamespace.gungame.data;

import com.yourgamespace.gungame.utils.MapCreator;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class MapCreatorData {

    private static final HashMap<UUID, MapCreator> mapCreators = new HashMap<>();

    public static void addMapCreator(Player player, MapCreator mapCreator) {
        mapCreators.put(player.getUniqueId(), mapCreator);
    }

    public static  void removeMapCreator(Player player) {
        mapCreators.remove(player.getUniqueId());
    }

    public static MapCreator getMapCreator(Player player) {
        return mapCreators.get(player.getUniqueId());
    }

    public static boolean isPlayerInMapCreation(Player player) {
        return mapCreators.containsKey(player.getUniqueId());
    }

}
