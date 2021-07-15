package com.yourgamespace.gungame.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.yourgamespace.gungame.main.GunGame;
import com.yourgamespace.gungame.utils.PacketHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@SuppressWarnings("ALL")
public class PlayerDeath implements Listener {

    private final PacketHandler packetHandler = new PacketHandler();

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
        if(Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            Bukkit.getScheduler().runTaskLater(GunGame.getInstance(), () -> {
                PacketContainer respawnPacket = new PacketContainer(PacketType.Play.Server.RESPAWN);
                try {
                    GunGame.getProtocolManager().sendServerPacket(player, respawnPacket);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }, 5);
        }
    }
}
