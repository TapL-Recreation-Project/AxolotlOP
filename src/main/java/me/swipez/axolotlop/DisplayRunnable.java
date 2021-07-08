package me.swipez.axolotlop;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DisplayRunnable extends BukkitRunnable {
    @Override
    public void run() {
        if (AxolotlOP.isStarted){
            for (Player player : Bukkit.getOnlinePlayers()){
                BreedStats playerStats = AxolotlOP.playerBreedStats.get(player.getUniqueId());
                int level = playerStats.getLevel();
                int bredCount = playerStats.getBredCount();

                ChatColor chatColor = null;
                if (level == 0){
                    chatColor = ChatColor.GRAY;
                }
                if (level == 1){
                    chatColor = ChatColor.RED;
                }
                if (level == 2){
                    chatColor = ChatColor.AQUA;
                }
                if (level == 3){
                    chatColor = ChatColor.GREEN;
                }
                if (level == 4){
                    chatColor = ChatColor.GOLD;
                }
                if (level == 5){
                    chatColor = ChatColor.LIGHT_PURPLE;
                }
                sendDisplayMessage(player, chatColor, level, bredCount);
            }
        }
    }

    public void sendDisplayMessage(Player player, ChatColor chatColor, int level, int breedCount){
        if (level != 5){
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(chatColor+"Level "+level+" "+ChatColor.BLACK+"| "+chatColor+breedCount+" axolotls bred"));
        }
        else {
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(chatColor+"Level MAX "+ChatColor.BLACK+"| "+chatColor+breedCount+" axolotls bred"));
        }
    }
}
