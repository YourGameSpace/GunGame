package com.yourgamespace.gungame.files;

import com.yourgamespace.gungame.data.Data;
import com.yourgamespace.gungame.main.GunGame;
import de.tubeof.tubetils.api.cache.CacheContainer;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class PluginConfig {

    private final ConsoleCommandSender ccs = Bukkit.getConsoleSender();
    private final Data data = GunGame.getData();
    private final CacheContainer cacheContainer = GunGame.getCacheContainer();

    public PluginConfig() {}

    private final File file = new File("plugins/GunGame", "Config.yml");
    private final FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

    public void configUpdateMessage() {
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§e######################################################################");
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§cA new config is included in the update!");
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§cPlease delete the old config so that the changes will be applied.");
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§e######################################################################");
    }

    private void saveCfg() {
        try {
            cfg.save(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void cfgConfig() {
        cfg.options().copyDefaults(true);

        //Messages
        cfg.addDefault("Messages.Prefix", "§7[§cGunGame§7] ");
        cfg.addDefault("Messages.Error.PlayerNotOnline", "§cThe player is not online!");
        cfg.addDefault("Messages.Error.NoPerms", "§cNo permissions!");

        //Settings
        cfg.addDefault("Settings.General.ServerMode.BungeeCord", true);
        cfg.addDefault("Settings.Updates.UseUpdateChecker", true);
        cfg.addDefault("Settings.Updates.ConsoleNotify", true);
        cfg.addDefault("Settings.Updates.IngameNotify", true);

        cfg.addDefault("ConfigVersion", data.getCurrentConfigVersion());

        saveCfg();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception localExeption) {
                ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§cConfig.yml could not be created!");
            }
        }
    }

    public void setCache() {
        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aConfig values are loaded into the cache ...");

        //Messages
        cacheContainer.add(String.class, "PREFIX", cfg.getString("Messages.Prefix"));
        cacheContainer.add(String.class, "ERROR_PLAYER_NOT_ONLINE", cfg.getString("Messages.Error.PlayerNotOnline"));
        cacheContainer.add(String.class, "ERROR_NO_PERMISSIONS", cfg.getString("Messages.Error.NoPerms"));

        //Settings
        cacheContainer.add(Boolean.class, "SERVERMODE_BUNGEECORD", cfg.getBoolean("Settings.General.ServerMode.BungeeCord"));
        cacheContainer.add(Boolean.class, "USE_UPDATE_CHECKER", cfg.getBoolean("Settings.Updates.UseUpdateChecker"));
        cacheContainer.add(Boolean.class, "UPDATE_NOTIFY_CONSOLE", cfg.getBoolean("Settings.Updates.ConsoleNotify"));
        cacheContainer.add(Boolean.class, "UPDATE_NOTIFY_INGAME", cfg.getBoolean("Settings.Updates.IngameNotify"));

        cacheContainer.add(Integer.class, "CONFIG_VERSION", cfg.getInt("ConfigVersion"));

        ccs.sendMessage(cacheContainer.get(String.class, "STARTUP_PREFIX") + "§aConfig values were successfully cached!");
    }
}
