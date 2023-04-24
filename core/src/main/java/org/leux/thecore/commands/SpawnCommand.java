package org.leux.thecore.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.leux.TheCore;
import org.leux.theapi.command.Command;
import org.leux.theapi.hook.hooks.VaultHook;
import org.leux.theapi.utils.ColorUtils;
import org.leux.theapi.utils.TaskUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.leux.thecore.configuration.Config.SPAWN_COOLDOWN;

public class SpawnCommand extends Command implements CommandExecutor, TabCompleter {

    private static HashMap<String, Location> spawnLocations;
    private static File file;
    private final JavaPlugin plugin;
    private static YamlConfiguration config;

    public SpawnCommand(String name, String description, boolean tabCompleter) {
        super(TheCore.getInstance());
        this.plugin = TheCore.getInstance();
        file = new File(plugin.getDataFolder(), "spawns.yml");
        config = YamlConfiguration.loadConfiguration(file);
        plugin.getCommand(name).setExecutor(this);
        plugin.getCommand(name).setName(name);
        plugin.getCommand(name).setDescription(description);
        if (tabCompleter) {
            plugin.getCommand(name).setTabCompleter(this);
        }
        loadSpawnLocations();
    }

    public static void init() {
        new SpawnCommand(
                "spawn",
                "spawn kommando",
                true
        );
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            String playerGroup = VaultHook.getPlayerGroups(player)[0];
            String spawnName = args.length > 0 ? args[0].toLowerCase() : "default";
            //TODO: fiks det her lort, det giver stress AAAAAAAAAAAAA
            sender.sendMessage(VaultHook.getPlayerGroups(player));
            if (!spawnName.equals("default")) {
                if (!player.hasPermission("thecore.spawn.teleport.*") || !player.hasPermission("thecore.spawn.teleport." + spawnName)) {
                    spawnName = "default";
                }
            }
            if (spawnLocations.containsKey(playerGroup)) {
                spawnName = playerGroup;
            }
            if (spawnLocations.containsKey(spawnName)) {
                if (player.hasPermission("thecore.spawn.cooldown.bypass")) {
                    sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix()) + " Du blev teleporteret til §bspawn");
                    player.teleport(spawnLocations.get(spawnName));
                } else {
                    Location playerLocation = player.getLocation();
                    String finalSpawnName = spawnName;
                    TaskUtils.runAsync(plugin, new Runnable() {
                        int timeLeft = SPAWN_COOLDOWN.getInteger();
                        @Override
                        public void run() {
                            if (player.getLocation().equals(playerLocation)) {
                                if (timeLeft > 0) {
                                    sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix()) + " Du bliver teleporteret til §bspawn §7om §b" + timeLeft + " §7sekunder.");
                                    timeLeft--;
                                    TaskUtils.runAsyncLater(plugin, this, 20L);
                                } else {
                                    sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix()) + " Du blev teleporteret til §bspawn");
                                    player.teleport(spawnLocations.get(finalSpawnName));
                                }
                            } else {
                                sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix()) + " Du blev ikke teleporteret til §bspawn, §7fordi du bevægede dig.");
                            }
                        }
                    });
                }
            } else {
                sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix()) + " Spawn §b" + spawnName + " §7findes ikke.");
            }
        } else {
            sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix()) + " Du skal være en spiller for at kunne bruge denne kommando.");
        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            spawnLocations.keySet().forEach(key -> {
                if (player.hasPermission("thecore.spawn.teleport.*") || player.hasPermission("thecore.spawn.teleport." + key) && !key.equals("default")) {
                    tabComplete.add(key);
                }
            });
        }
        return tabComplete;
    }

    private void loadSpawnLocations() {
        spawnLocations = new HashMap<>();
        file = new File(this.plugin.getDataFolder(), "spawns.yml");
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
                Location location = new Location(this.plugin.getServer().getWorld(world), x, y, z, yaw, pitch);
                spawnLocations.put(key, location);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setSpawn(String group, Location location) throws IOException {
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