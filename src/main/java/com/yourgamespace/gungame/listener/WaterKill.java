package com.yourgamespace.gungame.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class WaterKill implements Listener {

    @EventHandler
    public void onWaterTouch(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Block block = player.getLocation().getBlock();
        if(block.getType().equals(Material.WATER)) player.damage(40F);
    }
}
