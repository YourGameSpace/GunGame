package com.yourgamespace.gungame.listener;

import com.yourgamespace.gungame.main.GunGame;
import com.yourgamespace.gungame.utils.PacketHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

import java.lang.reflect.Constructor;
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
        Bukkit.getScheduler().runTaskLater(GunGame.getInstance(), () -> {
            try {
                Object enumDifficulty = packetHandler.getNMS("EnumDifficulty").getMethod("getById", int.class).invoke(null, 0);
                Object worldType = packetHandler.getNMS("WorldType").getDeclaredField(player.getWorld().getWorldType().toString()).get(null);
                Object enumGameMode = null;
                try {
                    enumGameMode = packetHandler.getNMS("EnumGamemode").getMethod("getById", int.class).invoke(null, player.getGameMode().ordinal());
                } catch (Exception ex) {
                    enumGameMode = packetHandler.getNMS("WorldSettings").getDeclaredClasses()[0].getMethod("valueOf", String.class).invoke(null, player.getGameMode().toString());
                }

                Constructor<?> respawnConstructor = packetHandler.getNMS("PacketPlayOutRespawn").getConstructor(int.class, enumDifficulty.getClass(), worldType.getClass(), enumGameMode.getClass());
                Object respawnPacket = respawnConstructor.newInstance(0, enumDifficulty, worldType, enumGameMode);
                packetHandler.sendPacket(player, respawnPacket);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException | InstantiationException e) {
                e.printStackTrace();
            }
        }, 5);
    }
}
