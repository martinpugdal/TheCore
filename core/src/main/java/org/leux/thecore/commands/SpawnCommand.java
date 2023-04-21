package org.leux.thecore.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.leux.TheCore;
import org.leux.theapi.command.CommandResult;
import org.leux.theapi.command.ICommand;
import org.leux.theapi.command.ISubCommand;
import org.leux.theapi.utils.ColorUtils;
import org.leux.theapi.utils.TaskUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SpawnCommand extends ICommand implements CommandExecutor, TabCompleter {

    private static HashMap<String, Location> spawnLocations;
    private File file;
    private final TheCore plugin;
    private YamlConfiguration config;

    public SpawnCommand(String name, String description, List<String> aliases, ArrayList<ISubCommand> subCommands, boolean tabCompleter) {
        super(TheCore.getInstance());
        this.plugin = TheCore.getInstance();
        this.file = new File(plugin.getDataFolder(), "spawns.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        plugin.getCommand(name).setExecutor(this);
        plugin.getCommand(name).setName(name);
        plugin.getCommand(name).setDescription(description);
        if (tabCompleter) {
            plugin.getCommand(name).setTabCompleter(this);
        }
        if (aliases != null) {
            plugin.getCommand(name).setAliases(aliases);
        }
        for (ISubCommand subCommand : subCommands) {
            super.addSubCommand(subCommand);
        }
        loadSpawnLocations();
    }

    public static void init() {
        new SpawnCommand(
                "spawn",
                "spawn kommando",
                Collections.singletonList("corespawn"),
                null,
                true
        );
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String spawnName = args[0].length() == 0 ? "default" : args[0];
            if (spawnLocations.containsKey(spawnName)) {
                if (player.hasPermission("thecore.spawn.cooldown.bypass")) {
                    sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix()) + " Du blev teleporteret til §b" + spawnName);
                    player.teleport(spawnLocations.get(spawnName));
                } else {
                    int cooldown = 5;
                    sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix()) + " Du bliver teleporteret til §b" + spawnName + " §7om §b" + cooldown + " §7sekunder.");
                    Location playerLocation = player.getLocation();
                    if (playerLocation != player.getLocation()) {
                        sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix()) + " Du blev ikke teleporteret til §b" + spawnName + " §7fordi du bevægede dig.");
                    }
                }
            } else {
                sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix()) + " Spawn §b" + spawnName + " §7findes ikke.");
            }
            return true;
        } else {
            sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix()) + " Du skal være en spiller for at kunne bruge denne kommando.");
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] strings) {
        return new ArrayList<>(spawnLocations.keySet());
    }

    private void loadSpawnLocations() {
        spawnLocations = new HashMap<>();
        file = new File(TheCore.getInstance().getDataFolder(), "spawns.yml");
        try {
            if (file.exists()) {
                config = YamlConfiguration.loadConfiguration(file);
            } else {
                config = new YamlConfiguration();
                Location defaultLocation = TheCore.getInstance().getServer().getWorlds().get(0).getSpawnLocation();
                setSpawn("default", defaultLocation);
                config.save(file);
            }
            config.getKeys(false).forEach(key -> {
                String world = config.getString(key + ".world");
                double x = config.getDouble(key + ".x");
                double y = config.getDouble(key + ".y");
                double z = config.getDouble(key + ".z");
                float yaw = (float) config.getDouble(key + ".yaw");
                float pitch = (float) config.getDouble(key + ".pitch");
                Location location = new Location(TheCore.getInstance().getServer().getWorld(world), x, y, z, yaw, pitch);
                spawnLocations.put(key, location);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSpawn(String group, Location location) throws IOException {
        group = group.toLowerCase();
        spawnLocations.put(group, location);
        config.set(group+".world", location.getWorld().getName());
        config.set(group+".x", location.getX());
        config.set(group+".y", location.getY());
        config.set(group+".z", location.getZ());
        config.set(group+".yaw", location.getYaw());
        config.set(group+".pitch", location.getPitch());
        config.save(file);
    }

    public static Location getSpawn(String group) {
        return spawnLocations.get(group);
    }

    public static HashMap<String, Location> getSpawnLocations() {
        return spawnLocations;
    }

}