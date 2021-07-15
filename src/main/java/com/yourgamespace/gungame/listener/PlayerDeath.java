package com.yourgamespace.gungame.listener;

import com.yourgamespace.gungame.main.GunGame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

import java.util.Objects;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        event.setDeathMessage(null);
        event.setKeepLevel(true);
        event.setKeepInventory(true);

        player.setHealth(20.0D);
        player.setVelocity(new Vector(0.0D, 0.0D, 0.0D));
        player.setFallDistance(0.0F);
        player.teleport(Objects.requireNonNull(player.getLocation().getWorld()).getSpawnLocation());
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        //For 1.13+
        Bukkit.getScheduler().scheduleSyncDelayedTask(GunGame.getInstance(), () -> player.spigot().respawn(), 5);
    }
}
