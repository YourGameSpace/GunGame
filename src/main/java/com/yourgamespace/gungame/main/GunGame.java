package com.yourgamespace.gungame.main;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.yourgamespace.gungame.commands.GunGameCMD;
import com.yourgamespace.gungame.data.Data;
import com.yourgamespace.gungame.files.PluginConfig;
import com.yourgamespace.gungame.listener.CancelEvents;
import com.yourgamespace.gungame.listener.CreatorCancelEvents;
import com.yourgamespace.gungame.listener.PlayerDeath;
import com.yourgamespace.gungame.listener.WaterKill;
import com.yourgamespace.gungame.utils.FolderUtils;
import com.yourgamespace.gungame.utils.ObjectTransformer;
import de.tubeof.tubetils.api.cache.CacheContainer;
import de.tubeof.tubetils.api.updatechecker.UpdateChecker;
import de.tubeof.tubetils.api.updatechecker.enums.ApiMethode;
import de.tubeof.tubetilsmanager.TubeTilsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

@SuppressWarnings("ALL")
public class GunGame extends JavaPlugin {

    private final ConsoleCommandSender ccs = Bukkit.getConsoleSender();
    private final PluginManager pluginManager = Bukkit.getPluginManager();

    private static GunGame main;
    private static TubeTilsManager tubeTilsManager;
    private static ProtocolManager protocolManager;
    private static CacheContainer cacheContainer;
    private static UpdateChecker updateChecker;
    private static Data data;
    private static PluginConfig pluginConfig;

    @Override
    public void onEnable() {
        initialisation();
        selfTests();

        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aThe Plugin will be activated ...");
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "==================================================");
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "JOIN OUR DISCORD: §ehttps://discord.gg/73ZDfbx");
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "==================================================");

        manageConfigs();
        //checkUpdate();

        registerListener();
        registerCommands();

        loadMaps();
        loadArenas();

        bStats();
    }

    @Override
    public void onDisable() {
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aThe Plugin will be deactivated ...");

        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aThe plugin was successfully deactivated!");
    }

    private void selfTests() {
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aRunning Self-Tests ...");

        //Load Project Properties
        Properties properties = new Properties();
        try {
            properties.load(getClassLoader().getResourceAsStream("project.properties"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        //Propertys Getter
        String projectVersion = properties.getProperty("version");
        Integer projectJavaVersion = Integer.parseInt(properties.getProperty("javaVersion"));

        //START: Java Test
        String javaVersion = System.getProperty("java.version");
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§eDetected Java-Version: " + javaVersion);

        //Format Java Version String
        if(javaVersion.startsWith("1.")) {
            javaVersion = javaVersion.substring(2, 3);
        } else {
            int dot = javaVersion.indexOf(".");
            if(dot != -1) {
                javaVersion = javaVersion.substring(0, dot);
            }
        }
        // Final Java Check
        if(!(Integer.parseInt(javaVersion) >= projectJavaVersion)) {
            ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§cJava-Version does not match required Java-Version! Stopping plugin ...");
            pluginManager.disablePlugin(getInstance());
            return;
        }
        //END: Java Test

        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aSelf-Tests done!");
    }

    private void initialisation() {
        main = this;

        tubeTilsManager = new TubeTilsManager("§7[§cGunGameLogger§7] ", this, "SNAPSHOT-48", "1.0.2", true);
        cacheContainer = new CacheContainer("GunGame");
        cacheContainer.registerCacheType(String.class);
        cacheContainer.registerCacheType(Boolean.class);
        cacheContainer.registerCacheType(Integer.class);
        cacheContainer.registerCacheType(ArrayList.class);
        cacheContainer.add(String.class, "STARTUP_PREFIX", "§7[§cGunGameLogger§7] ");

        data = new Data();
        pluginConfig = new PluginConfig();

        //ProtocolLib
        if(Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aProtocolLib is installed! Support for ProtocolLib enabled!");
            data.setProtocollib(true);

            protocolManager = ProtocolLibrary.getProtocolManager();
        } else {
            data.setProtocollib(false);
            ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§cProtocolLib is NOT installed! Support for ProtocolLib disabled!");
        }
    }

    private void manageConfigs() {
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aLoading Config Files ...");

        pluginConfig.cfgConfig();
        pluginConfig.setCache();
        if(ObjectTransformer.getInteger(cacheContainer.get(Integer.class, "CONFIG_VERSION")) != data.getCurrentConfigVersion()) pluginConfig.configUpdateMessage();

        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aConfig Files was successfully loaded!");
    }

    private void checkUpdate() {
        if(!ObjectTransformer.getBoolean(cacheContainer.get(Boolean.class, "USE_UPDATE_CHECKER"))) {
            ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§cCheck for updates disabled. The check will be skipped!");
            return;
        }

        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aChecking for updates ...");
        try {
            updateChecker = new UpdateChecker(0, this, ApiMethode.YOURGAMESPACE, false, true);
            if(updateChecker.isOutdated()) {
                if(ObjectTransformer.getBoolean(cacheContainer.get(Boolean.class, "UPDATE_NOTIFY_CONSOLE"))) ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§cAn update was found! (v" + updateChecker.getLatestVersion() + ") Download here: " + updateChecker.getDownloadUrl());
            }
        } catch (IOException exception) {
            ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§cAn error occurred while checking for updates!");
            exception.printStackTrace();
        }
    }

    private void registerListener() {
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aListeners will be registered ...");

        pluginManager.registerEvents(new CreatorCancelEvents(), this);
        pluginManager.registerEvents(new CancelEvents(), this);
        pluginManager.registerEvents(new WaterKill(), this);
        pluginManager.registerEvents(new PlayerDeath(), this);

        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aListeners have been successfully registered!");
    }

    private void registerCommands() {
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aCommands will be registered ...");

        getCommand("gungame").setExecutor(new GunGameCMD());

        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aCommands have been successfully registered!");
    }

    private void loadMaps() {
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aLoading and caching maps ...");

        File mapConfigFolder = new File(data.getMapConfigPath());
        try {
            if(FolderUtils.isEmpty(Paths.get(mapConfigFolder.getPath())) || !Files.exists(Paths.get(mapConfigFolder.getPath()))) {
                ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§cNo map configs found! Has an map already been created?");
            }
        } catch (IOException exception) {
            ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aError while reading map configs!");

            exception.printStackTrace();
            pluginManager.disablePlugin(getInstance());
            return;
        }

        ArrayList<String> maps = new ArrayList<>();

        for (File fileEntry : mapConfigFolder.listFiles()) {
            if(!fileEntry.isFile()) continue;

            FileConfiguration cfg = YamlConfiguration.loadConfiguration(fileEntry);
            String mapName = cfg.getString("MapName");

            maps.add(mapName);
            ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aMap §e" + mapName + " §asuccessfully loaded and cached!");
        }

        cacheContainer.add(ArrayList.class, "MAPS", maps);

        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aMaps have been successfully loaded!");
    }

    private void loadArenas() {
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aLoading and caching arenas ...");

        File arenaConfigFolder = new File(data.getArenaConfigPath());

        try {
            if(FolderUtils.isEmpty(Paths.get(arenaConfigFolder.getPath())) || !Files.exists(Paths.get(arenaConfigFolder.getPath()))) {
                ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§cNo arena configs found! Has an arena already been created?");
                return;
            }
        } catch (IOException exception) {
            ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aError while reading arena configs!");

            exception.printStackTrace();
            pluginManager.disablePlugin(getInstance());
            return;
        }

        ArrayList<String> arenas = new ArrayList<>();

        for (File fileEntry : arenaConfigFolder.listFiles()) {
            if(!fileEntry.isFile()) continue;

        }

        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aArenas have been successfully loaded!");
    }

    private void bStats() {

    }

    public static ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public static PluginConfig getPluginConfig() {
        return pluginConfig;
    }

    public static Data getData() {
        return data;
    }

    public static UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    public static TubeTilsManager getTubeTilsManager() {
        return tubeTilsManager;
    }

    public static CacheContainer getCacheContainer() {
        return cacheContainer;
    }

    public static GunGame getInstance() {
        return main;
    }
}
