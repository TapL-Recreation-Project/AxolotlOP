package me.swipez.axolotlop;

import me.swipez.axolotlop.command.StartCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.UUID;

public final class AxolotlOP extends JavaPlugin {

    public static HashMap<UUID, BreedStats> playerBreedStats = new HashMap<>();

    public static AxolotlOP plugin;

    public static boolean isStarted = false;

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getPluginManager().registerEvents(new AxolotlBreedListener(), this);
        ItemManager.initRecipes();
        BukkitTask task = new DisplayRunnable().runTaskTimer(this, 1, 1);
        getCommand("axolotlop").setExecutor(new StartCommand());
        saveDefaultConfig();
        getConfig().options().copyDefaults();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
