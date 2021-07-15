package com.yourgamespace.gungame.listener;

import com.cryptomorin.xseries.XMaterial;
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
        XMaterial material = XMaterial.matchXMaterial(block.getType());

        if(material == XMaterial.WATER) player.damage(40F);
    }
}
