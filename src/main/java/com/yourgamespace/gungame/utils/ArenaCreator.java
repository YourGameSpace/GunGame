package com.yourgamespace.gungame.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@SuppressWarnings("unused")
public class ArenaCreator {

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

        private String arenaName;
        private String arenaMap;

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

        public void setArenaName(String arenaName) {
            this.arenaName = arenaName;
        }

        public String getArenaName() {
            return arenaName;
        }

        public void setArenaMap(String arenaMap) {
            this.arenaMap = arenaMap;
        }

        public String getArenaMap() {
            return arenaMap;
        }
    }

}
