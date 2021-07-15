package com.yourgamespace.gungame.utils;

import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.yourgamespace.gungame.main.GunGame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static com.comphenix.protocol.PacketType.Play.Server.RESPAWN;

@SuppressWarnings("ALL")
public class PacketHandler {

    private final ProtocolManager protocolManager = GunGame.getProtocolManager();

    public PacketHandler() {}

    public Class<?> getNMS(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendPacket(Player player, Object packet) {
        try {
            Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
            Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
            playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMS("Packet") }).invoke(playerConnection, new Object[] { packet });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendRespawnPlayerPacket(Player player) throws ReflectiveOperationException {
        PacketContainer respawn = new PacketContainer(RESPAWN);
        EnumWrappers.Difficulty difficulty = EnumWrappers.getDifficultyConverter().getSpecific(player.getWorld().getDifficulty());
        //<= 1.13.1
        int dimensionId = player.getWorld().getEnvironment().getId();
        respawn.getIntegers().writeSafely(0, dimensionId);
        //> 1.13.1
        if (MinecraftVersion.getCurrentVersion().compareTo(MinecraftVersion.AQUATIC_UPDATE) > 0) {
            try {
                respawn.getDimensions().writeSafely(0, dimensionId);
            } catch (NoSuchMethodError noSuchMethodError) {
                throw new ReflectiveOperationException("Unable to find dimension setter. " +
                        "Your ProtocolLib version is incompatible with this plugin version in combination with " +
                        "Minecraft 1.13.1. " +
                        "Try to download an update of ProtocolLib.", noSuchMethodError);
            }
        }
        respawn.getDifficulties().write(0, difficulty);
        respawn.getGameModes().write(0, EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode()));
        respawn.getWorldTypeModifier().write(0, player.getWorld().getWorldType());
        protocolManager.sendServerPacket(player, respawn);
    }
}
