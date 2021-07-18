package com.yourgamespace.gungame.listener;

import com.yourgamespace.gungame.data.Data;
import com.yourgamespace.gungame.main.GunGame;
import com.yourgamespace.gungame.utils.PacketHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

@SuppressWarnings("ALL")
public class PlayerDeath implements Listener {

    private final Data data = GunGame.getData();
    private final PluginManager pluginManager = Bukkit.getPluginManager();
    private final PacketHandler packetHandler = new PacketHandler();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        event.setDeathMessage(null);
        event.setKeepLevel(true);
        event.setKeepInventory(true);

        Location respawnLocation = player.getWorld().getSpawnLocation();

        player.setHealth(20.0D);
        player.setVelocity(new Vector(0.0D, 0.0D, 0.0D));
        player.setFallDistance(0.0F);
        player.teleport(respawnLocation);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        pluginManager.callEvent(new PlayerRespawnEvent(player, respawnLocation, false));

        //For 1.13+
        if(data.isProtocollibInstalled()) {
            Bukkit.getScheduler().runTaskLater(GunGame.getInstance(), () -> {
                try {
                    packetHandler.sendPlayerRespawnPacket(player);
                    packetHandler.sendPlayerTeleportPacket(player);
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
            }, 1);
        }
    }
}
