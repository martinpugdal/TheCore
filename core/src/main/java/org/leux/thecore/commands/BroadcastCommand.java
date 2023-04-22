package org.leux.thecore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.leux.TheCore;
import org.leux.theapi.command.ICommand;
import org.leux.theapi.utils.ColorUtils;

import java.util.ArrayList;
import java.util.Arrays;


public class BroadcastCommand extends ICommand implements CommandExecutor {

    private final JavaPlugin plugin;

    public BroadcastCommand(String name, String description, ArrayList<String> aliases) {
        super(TheCore.getInstance());
        this.plugin = TheCore.getInstance();
        plugin.getCommand(name).setExecutor(this);
        plugin.getCommand(name).setName(name);
        plugin.getCommand(name).setDescription(description);
        if (aliases != null) {
            plugin.getCommand(name).setAliases(aliases);
        }
    }

    public static void init() {
        new BroadcastCommand(
                "bc",
                "broadcast kommando",
                new ArrayList<>(Arrays.asList("broadcast"))
        );
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!sender.hasPermission("thecore.command.broadcast")) {
            sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix() + " &fDu har ikke adgang til denne kommando."));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix() + " &fBrug: &7/broadcast <besked>"));
            return true;
        }

        StringBuilder message = new StringBuilder();
        for (String arg : args) {
            message.append(arg).append(" ");
        }

        Bukkit.broadcastMessage(ColorUtils.getColored(TheCore.getPrefix() + " " + message.toString()));
        return true;
    }
}