package com.yourgamespace.gungame.commands;

import com.yourgamespace.gungame.data.Data;
import com.yourgamespace.gungame.data.MapCreatorData;
import com.yourgamespace.gungame.files.MapConfig;
import com.yourgamespace.gungame.main.GunGame;
import com.yourgamespace.gungame.utils.FolderUtils;
import com.yourgamespace.gungame.utils.MapCreator;
import com.yourgamespace.gungame.utils.ObjectTransformer;
import de.tubeof.tubetils.api.cache.CacheContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.nio.file.*;
import java.util.Objects;

@SuppressWarnings({"NullableProblems", "unused"})
public class GunGameCMD implements CommandExecutor {

    private final Data data = GunGame.getData();
    private final CacheContainer cacheContainer = GunGame.getCacheContainer();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§cYou have to be a player!");
            return true;
        }
        Player player = (Player) commandSender;
        if(args.length == 0) {
            sendUsageMessage(player);
            return true;
        }
        String subCommand = args[0];

        if(subCommand.equalsIgnoreCase("loadWorld")) {
            if(args.length != 2) {
                player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§c/gungame loadWorld <World>");
                return true;
            }

            String worldName = args[1];

            //CHECK: Does world exists
            if(Files.notExists(Paths.get(worldName), LinkOption.NOFOLLOW_LINKS)) {
                player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§cNo world with name §e" + worldName + " §cfound!");
                return true;
            }
            //CHECK: Is world already loded
            if(Bukkit.getWorld(worldName) != null) {
                player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§aWorld already loaded! Teleporting ...");

                player.teleport(Objects.requireNonNull(Bukkit.getWorld(worldName)).getSpawnLocation());
                return true;
            }

            //Load World
            player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§aLoading world with name §e" + worldName + " §a... ");

            WorldCreator worldCreator = new WorldCreator(worldName).environment(World.Environment.NORMAL);
            World world = Bukkit.getServer().createWorld(worldCreator);
            Bukkit.getWorlds().add(world);

            player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§aWorld successfully loaded!");

            player.teleport(Objects.requireNonNull(Bukkit.getWorld(worldName)).getSpawnLocation());
            return true;
        }

        if(subCommand.equalsIgnoreCase("createMap")) {
            MapCreator mapCreator;
            if(MapCreatorData.isPlayerInMapCreation(player)) mapCreator = MapCreatorData.getMapCreator(player);
            else mapCreator = new MapCreator(player); MapCreatorData.addMapCreator(player, mapCreator);

            int step = mapCreator.getCurrentStep();
            //STEP: Set Map Name
            if(step == 1) {
                if(args.length != 2) {
                    player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§c/gungame createMap <Mapname>");
                    return true;
                }

                String mapName = args[1];
                mapCreator.setMapName(mapName);
                mapCreator.addStep();
                player.sendMessage(String.valueOf(mapCreator.getCurrentStep()));

                player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§aMapname was set to §e" + mapName + "§a!");
                player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§2NEXT STEP: §7Set the spawn protection radius where PVP is disabled. Use the command below:");
                player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§2NEXT STEP: §7/gungame createMap <Spawn-Protection-Radius>");

                return true;
            }
            //STEP: Set Spawn Protection Radius
            if(step == 2) {
                if(args.length != 2) {
                    player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§c/gungame createMap <Spawn-Protection-Radius>");
                    return true;
                }

                int spawnLocationRadius;
                try {
                    spawnLocationRadius = Integer.parseInt(args[1]);
                } catch (NumberFormatException exception) {
                    player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§cSpawn-Protection-Radius must be a number!");
                    player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§c/gungame createMap <Spawn-Protection-Radius>");
                    return true;
                }

                mapCreator.setSpawnProtectionRadius(spawnLocationRadius);
                mapCreator.addStep();
                player.sendMessage(String.valueOf(mapCreator.getCurrentStep()));

                player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§aSpawn-Protection-Radius was set to §e" + spawnLocationRadius + "§a!");
                player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§2NEXT STEP: §7Set the spawn location for this map. Stand at the desired position and execute the following command:");
                player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§2NEXT STEP: §7/gungame createMap setSpawnLocation");
                return true;
            }
            //STEP: Set Spawn Location
            if(step == 3) {
                if(args.length != 2) {
                    player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§c/gungame createMap setSpawnLocation");
                    return true;
                }

                mapCreator.setSpawnLocation(player.getLocation());
                mapCreator.addStep();
                player.sendMessage(String.valueOf(mapCreator.getCurrentStep()));

                player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§aSpawn-Location set!");
                player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§eThe map has now been successfully set up! You can now make your own settings in the world, such as Gamerules or Worldborder. The settings will be adopted.");
                player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§2NEXT STEP: §8Complete the map creation if you do not want to make any more changes. You will be kicked out of the world and the map config will be created! Use the command below:");
                player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§2NEXT STEP: §7/gungame createMap finish");
                return true;
            }
            //STEP: Finish
            if(step == 4) {
                if(args.length != 2) {
                    player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§c/gungame createMap finish");
                    return true;
                }
                MapCreatorData.removeMapCreator(player);

                World world = player.getWorld();
                Path path = Paths.get(world.getWorldFolder().getPath());

                for(Player worldPlayers : world.getPlayers()) {
                    worldPlayers.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
                }
                Bukkit.unloadWorld(world, true);

                try {
                    FolderUtils.copyFolder(path, Paths.get(data.getMapStoragePath() + "/" + mapCreator.getMapName()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException exception) {
                    player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§cError while trying world to Map-Storage! Check the console for more details.");

                    exception.printStackTrace();
                    return true;
                }

                MapConfig mapConfig = new MapConfig(mapCreator.getMapName());
                mapConfig.createMapConfig(mapCreator.getSpawnProtectionRadius(), mapCreator.getSpawnLocation());

                player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§aMap creation successfully completed! You can now create arenas and assign this map to it with the following command:");
                player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§e/gungame createArena");
                return true;
            }

            return true;
        }

        return true;
    }

    private void sendUsageMessage(Player player) {
        player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§7--- §cGunGame Commands Overview §7---");
        player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§7> §c/gungame loadWorld <World>");
        player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "§7> §c/gungame createMap");
        player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "");
        player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "");
        player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "");
        player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "");
        player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "");
        player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "");
        player.sendMessage(ObjectTransformer.getString(cacheContainer.get(String.class, "PREFIX")) + "");
    }
}
