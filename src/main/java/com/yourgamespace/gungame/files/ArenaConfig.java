package com.yourgamespace.gungame.files;

import com.yourgamespace.gungame.data.Data;
import com.yourgamespace.gungame.main.GunGame;
import de.tubeof.tubetils.api.cache.CacheContainer;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ArenaConfig {

    private final ConsoleCommandSender ccs = Bukkit.getConsoleSender();
    private final Data data = GunGame.getData();
    private final CacheContainer cacheContainer = GunGame.getCacheContainer();

    private final String arenaName;

    public ArenaConfig(String arenaName) {
        this.arenaName = arenaName;

        configInitialisation();
    }

    private File file;
    private FileConfiguration cfg;

    private void configInitialisation() {
        file = new File(data.getArenaConfigPath(), arenaName + ".yml");
        cfg = YamlConfiguration.loadConfiguration(file);
    }

    private void saveCfg() {
        try {
            cfg.save(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void createArenaConfig(String arenaMap, int arenaId) {
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aCreating new Arena-Config for Arena §e" + arenaName + " (ID: " + arenaId + ") §a...");

        //Default Arena Params
        cfg.set("ArenaName", arenaName);
        cfg.set("ArenaMap", arenaMap);
        cfg.set("ArenaId", arenaId);

        saveCfg();

        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aArena-Config successfully created!");
    }

}
