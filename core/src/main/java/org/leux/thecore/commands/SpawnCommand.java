package org.leux.thecore.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.leux.TheCore;
import org.leux.theapi.command.CommandResult;
import org.leux.theapi.command.ICommand;
import org.leux.theapi.command.ISubCommand;
import org.leux.theapi.utils.ColorUtils;
import org.leux.thecore.configuration.Config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpawnCommand extends ICommand implements CommandExecutor, TabCompleter {

    private final TheCore plugin;

    public SpawnCommand(String name, String description, List<String> aliases, ArrayList<ISubCommand> subCommands, boolean tabCompleter) {
        super(TheCore.getInstance());
        this.plugin = TheCore.getInstance();
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
    }

    public static void init() {
        new SpawnCommand(
                "spawn",
                "spawn kommando",
                Arrays.asList("corespawn"),
                null,
                true
        );
        loadSpawnLocations();
    }

    private static void loadSpawnLocations() {
        File file = new File(TheCore.getInstance().getDataFolder(), "config.yml");
        try {
            YamlConfiguration config;
            if (file.exists()) {
                config = YamlConfiguration.loadConfiguration(file);
            } else {
                config = new YamlConfiguration();
            }
            List<String> stringList = config.getStringList("spawns");
            for (String spawn : stringList) {
                spawn.split("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load(File file) {

    }
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        CommandResult commandResult = super.execute(sender, args);
        switch (commandResult.getResult()) {
            case SUCCESS:
                return true;
            case WRONG_USAGE:
                sender.sendMessage(ColorUtils.getColored(this.plugin.getPrefix()) + " Brug: §b" + commandResult.getSubCommand().getUsage(label));
                return true;
            case NO_PERMISSION:
                sender.sendMessage(ColorUtils.getColored(this.plugin.getPrefix()) + " Du har ikke adgang til at bruge denne kommando.");
                return true;
            default:
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] strings) {
        return Arrays.asList("default", "vip");
    }


    private void setSpawn(Location location, String group) {
    }

}