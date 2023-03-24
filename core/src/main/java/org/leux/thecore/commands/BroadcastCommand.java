package org.leux.thecore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.leux.TheCore;
import org.leux.theapi.command.ICommand;
import org.leux.theapi.utils.ColorUtils;

import java.util.Arrays;


public class BroadcastCommand extends ICommand implements CommandExecutor {

    private final TheCore plugin;

    public BroadcastCommand(TheCore plugin) {
        super(plugin);
        plugin.getCommand("bc").setExecutor(this);
        plugin.getCommand("bc").setAliases(Arrays.asList("broadcast"));
        this.plugin = plugin;
    }

    public static void init() {
        new BroadcastCommand(TheCore.getInstance());
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

        Bukkit.broadcastMessage(ColorUtils.getColored(plugin.getPrefix() + " " + message.toString()));
        return true;
    }
}