package org.leux.thecore.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.leux.TheCore;
import org.leux.theapi.command.Command;
import org.leux.theapi.command.CommandResult;
import org.leux.theapi.command.SubCommand;
import org.leux.theapi.utils.ColorUtils;
import org.leux.thecore.commands.subCore.InfoSubCommand;

public class DiscordCommand extends Command implements CommandExecutor {

    private final TheCore plugin;

    public DiscordCommand(TheCore plugin) {
        super(plugin);
        this.plugin = plugin;
        plugin.getCommand("discord").setExecutor(this);
    }

    public static void init() {
        new DiscordCommand(TheCore.getInstance());
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix() + " &ahttps://discord.gg/YNt7J43RRN"));
        return true;
    }
}