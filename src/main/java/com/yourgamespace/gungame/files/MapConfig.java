package com.yourgamespace.gungame.files;

import com.yourgamespace.gungame.data.Data;
import com.yourgamespace.gungame.main.GunGame;
import de.tubeof.tubetils.api.cache.CacheContainer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@SuppressWarnings("unused")
public class MapConfig {

    private final ConsoleCommandSender ccs = Bukkit.getConsoleSender();
    private final Data data = GunGame.getData();
    private final CacheContainer cacheContainer = GunGame.getCacheContainer();

    private final String mapName;

    public MapConfig(String mapName) {
        this.mapName = mapName;

        configInitialisation();
    }

    private File file;
    private FileConfiguration cfg;

    private void configInitialisation() {
        file = new File(data.getMapConfigPath(), mapName + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    private void saveCfg() {
        try {
            cfg.save(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createMapConfig(int spawnProtectionRadius, Location spawnLocation) {
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aCreating new Map-Config for Map §e" + mapName + " §a...");

        //Default Map Params
        cfg.set("MapName", mapName);
        cfg.set("SpawnProtectionRadius", spawnProtectionRadius);

        //LOCATION: Spawn
        cfg.set("Spawn.x", spawnLocation.getX());
        cfg.set("Spawn.y", spawnLocation.getY());
        cfg.set("Spawn.z", spawnLocation.getZ());
        cfg.set("Spawn.yaw", spawnLocation.getYaw());
        cfg.set("Spawn.pitch", spawnLocation.getPitch());

        saveCfg();

        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aMap-Config successfully created!");
    }

}
