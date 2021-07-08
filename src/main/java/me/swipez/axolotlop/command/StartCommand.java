package me.swipez.axolotlop.command;

import me.swipez.axolotlop.AxolotlOP;
import me.swipez.axolotlop.BreedStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            if (sender.hasPermission("axolotlop.startcommand")){
                if (AxolotlOP.isStarted){
                    AxolotlOP.isStarted = false;
                    Bukkit.broadcastMessage(ChatColor.GRAY+"[!] Axolotl's are OP Challenge is toggled "+ChatColor.RED+"off");
                }
                else {
                    AxolotlOP.isStarted = true;
                    Bukkit.broadcastMessage(ChatColor.GRAY+"[!] Axolotl's are OP Challenge is toggled "+ChatColor.GREEN+"on");
                    for (Player allPlayers : Bukkit.getOnlinePlayers()){
                        AxolotlOP.playerBreedStats.put(allPlayers.getUniqueId(), new BreedStats(new int[]{1,10,25,50,100}));
                    }
                }
            }
            else {
                sender.sendMessage(ChatColor.RED+"[!] You do not have permission to run this command.");
            }
        }
        else {
            sender.sendMessage(ChatColor.RED+"[!] Only players can run this command!");
        }
        return true;
    }
}
