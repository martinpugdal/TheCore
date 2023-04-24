package org.leux.thecore.commands;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.leux.TheCore;
import org.leux.theapi.command.Command;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetSpawnCommand extends Command implements CommandExecutor {

    private final JavaPlugin plugin;

    public SetSpawnCommand(String name, String description, String permission) {
        super(TheCore.getInstance());
        this.plugin = TheCore.getInstance();
        plugin.getCommand(name).setExecutor(this);
        plugin.getCommand(name).setName(name);
        plugin.getCommand(name).setDescription(description);
        plugin.getCommand(name).setPermission(permission);
    }

    public static void init() {
        new SetSpawnCommand(
            "setspawn",
            "setspawn kommando",
            "thecore.spawn.setspawn"
        );
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location location = player.getLocation();
            String group = "default";
            if (args.length > 0) {
                Boolean view = false;
                if (args[0].equalsIgnoreCase("-v")) {
                    view = !view;
                    if (args.length > 1) group = args[1];
                } else if (args.length > 1 && args[1].equalsIgnoreCase("-v")) {
                    view = !view;
                    group = args[0];
                } else {
                    group = args[0];
                }
                if (view) {
                    location.setPitch(0);
                    location.setYaw(Math.round(location.getYaw()/45)*45);
                }
            }
            try {
                SpawnCommand.setSpawn(group, location);
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