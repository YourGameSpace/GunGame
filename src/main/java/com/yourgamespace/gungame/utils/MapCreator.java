package com.yourgamespace.gungame.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("unused")
public class MapCreator {

    public static class Data {
        private final HashMap<UUID, Creator> creators = new HashMap<>();

        public void addCreator(Player player, Creator creator) {
            creators.put(player.getUniqueId(), creator);
        }

        public void removeCreator(Player player) {
            creators.remove(player.getUniqueId());
        }

        public Creator getCreator(Player player) {
            return creators.get(player.getUniqueId());
        }

        public boolean isPlayerInCreation(Player player) {
            return creators.containsKey(player.getUniqueId());
        }
    }

    public static class Creator {
        private final UUID ownerUuid;
        private final String ownerName;
        private int step = 1;

        private String mapName;
        private int spawnProtectionRadius;
        private Location spawnLocation;

        public Creator(Player player) {
            this.ownerUuid = player.getUniqueId();
            this.ownerName = player.getName();
        }

        public int getCurrentStep() {
            return step;
        }

        public void addStep() {
            step++;
        }

        public UUID getOwnerUuid() {
            return ownerUuid;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setMapName(String mapName) {
            this.mapName = mapName;
        }

        public String getMapName() {
            return mapName;
        }

        public void setSpawnProtectionRadius(int radius) {
            spawnProtectionRadius = radius;
        }

        public int getSpawnProtectionRadius() {
            return spawnProtectionRadius;
        }

        public void setSpawnLocation(Location location) {
            spawnLocation = location;
        }

        public Location getSpawnLocation() {
            return spawnLocation;
        }
    }

}
