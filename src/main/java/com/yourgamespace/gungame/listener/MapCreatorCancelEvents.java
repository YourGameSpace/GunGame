package com.yourgamespace.gungame.listener;

import com.yourgamespace.gungame.data.MapCreatorData;
import com.yourgamespace.gungame.main.GunGame;
import com.yourgamespace.gungame.utils.ObjectTransformer;
import de.tubeof.tubetils.api.cache.CacheContainer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class MapCreatorCancelEvents implements Listener {

    private final CacheContainer cacheContainer = GunGame.getCacheContainer();

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        if(MapCreatorData.isPlayerInMapCreation(player)) {
            MapCreatorData.removeMapCreator(player);
            player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§cMap creation cancelled due to world change!");
        }
    }

}
