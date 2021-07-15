package com.yourgamespace.gungame.commands;

import com.yourgamespace.gungame.data.Data;
import com.yourgamespace.gungame.main.GunGame;
import de.tubeof.tubetils.api.cache.CacheContainer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings({"NullableProblems", "unused"})
public class GunGameCMD implements CommandExecutor {

    private final Data data = GunGame.getData();
    private final CacheContainer cacheContainer = GunGame.getCacheContainer();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(cacheContainer.get(String.class, "PREFIX") + "§cYou have to be a player!");
            return true;
        }
        Player player = (Player) commandSender;



        return true;
    }

    private void sendUsageMessage(Player player) {
        //player.sendMessage("");
    }
}
