package org.leux.thecore.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.leux.TheCore;
import org.leux.theapi.command.ICommand;
import org.leux.theapi.utils.ColorUtils;

public class DiscordCommand extends ICommand implements CommandExecutor {

    private final TheCore plugin;

    public DiscordCommand(String name, String description) {
        super(TheCore.getInstance());
        this.plugin = TheCore.getInstance();
        plugin.getCommand(name).setExecutor(this);
        plugin.getCommand(name).setName(name);
        plugin.getCommand(name).setDescription(description);
    }

    public static void init() {
        new DiscordCommand("discord", "få discord link til serveren");
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix() + " &ahttps://discord.gg/YNt7J43RRN"));
        return true;
    }
}