package com.yourgamespace.gungame.main;

import com.yourgamespace.gungame.data.Data;
import com.yourgamespace.gungame.files.PluginConfig;
import com.yourgamespace.gungame.listener.CancelEvents;
import com.yourgamespace.gungame.listener.WaterKill;
import com.yourgamespace.gungame.utils.ObjectTransformer;
import de.tubeof.tubetils.api.cache.CacheContainer;
import de.tubeof.tubetils.api.updatechecker.UpdateChecker;
import de.tubeof.tubetils.api.updatechecker.enums.ApiMethode;
import de.tubeof.tubetilsmanager.TubeTilsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Properties;

@SuppressWarnings("ALL")
public class GunGame extends JavaPlugin {

    private final ConsoleCommandSender ccs = Bukkit.getConsoleSender();
    private final PluginManager pluginManager = Bukkit.getPluginManager();

    private static GunGame main;
    private static TubeTilsManager tubeTilsManager;
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
        checkUpdate();

        registerListener();
        registerCommands();

        bStats();
    }

    @Override
    public void onDisable() {
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aThe Plugin will be deactivated ...");

        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aThe plugin was successfully deactivated!");
    }

    private void selfTests() {
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aRunning Self-Tests ...");

        Properties properties = new Properties();
        try {
            properties.load(getClassLoader().getResourceAsStream("project.properties"));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        String projectVersion = properties.getProperty("version");
        Integer projectJavaVersion = Integer.parseInt(properties.getProperty("javaVersion"));

        //Java Test
        if(!(Integer.parseInt(System.getProperty("java.version")) >= projectJavaVersion)) {
            ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§cJava-Version does not match required Java-Version! Stopping plugin ...");
            pluginManager.disablePlugin(getInstance());
            return;
        }

        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aSelf-Tests done!");
    }

    private void initialisation() {
        main = this;

        tubeTilsManager = new TubeTilsManager("§7[§cGunGameLogger§7] ", this, "SNAPSHOT-48", "1.0.2", true);
        cacheContainer = new CacheContainer("GunGame");
        cacheContainer.registerCacheType(String.class);
        cacheContainer.registerCacheType(Boolean.class);
        cacheContainer.registerCacheType(Integer.class);
        cacheContainer.add(String.class, "STARTUP_PREFIX", "§7[§cGunGameLogger§7] ");

        data = new Data();
        pluginConfig = new PluginConfig();
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

        pluginManager.registerEvents(new CancelEvents(), this);

        pluginManager.registerEvents(new WaterKill(), this);

        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aListeners have been successfully registered!");
    }

    private void registerCommands() {
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aCommands will be registered ...");

        //Commands

        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aCommands have been successfully registered!");
    }

    private void bStats() {

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
