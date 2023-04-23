package org.leux.thecore.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.leux.TheCore;
import org.leux.theapi.command.Command;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class SetSpawnCommand extends Command implements CommandExecutor {

    private final JavaPlugin plugin;

    public SetSpawnCommand(String name, String description, String permission, List<String> aliases) {
        super(TheCore.getInstance());
        this.plugin = TheCore.getInstance();
        plugin.getCommand(name).setExecutor(this);
        plugin.getCommand(name).setName(name);
        plugin.getCommand(name).setDescription(description);
        plugin.getCommand(name).setPermission(permission);
        plugin.getCommand(name).setAliases(aliases);
    }

    public static void init() {
        new SetSpawnCommand(
                "spawn",
                "setspawn kommando",
                "thecore.spawn.setspawn",
                Collections.singletonList("coresetspawn")
        );
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            try {
                SpawnCommand.setSpawn(args.length > 0 ? args[0] : "default", player.getLocation());
            } catch (IOException e) {
                e.printStackTrace();
            }
            sender.sendMessage("§aSpawn er nu sat til din position");
        } else {
            sender.sendMessage("§cDu har ikke adgang til denne kommando");
        }
        return true;
    }
}