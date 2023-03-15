package org.leux.thecore.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.leux.TheCore;
import org.leux.theapi.command.Command;
import org.leux.theapi.command.CommandResult;
import org.leux.theapi.command.SubCommand;
import org.leux.theapi.utils.ColorUtils;
import org.leux.thecore.commands.subCore.InfoSubCommand;

public class CoreCommand extends Command implements CommandExecutor {

    private final TheCore plugin;

    public CoreCommand(TheCore plugin) {
        super(plugin);
        this.plugin = plugin;
        plugin.getCommand("thecore").setExecutor(this);
        super.addSubCommand(new InfoSubCommand(plugin));
    }

    public static void init() {
        new CoreCommand(TheCore.getInstance());
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
                sendHelpMessage(sender, label);
                return true;
        }
    }

    /**
     * Send the help message to the sender.
     *
     * @param sender The sender to send the message to.
     * @param label  The label of the command.
     */
    private void sendHelpMessage(CommandSender sender, String label) {
        sender.sendMessage("§7§m----------------------------------------");
        sender.sendMessage("");
        sender.sendMessage(ColorUtils.getColored(this.plugin.getPrefix()) + " §bCommands:");
        for (SubCommand subCommand : super.getSubCommands()) {
            if (!hasPermission(sender, subCommand.getPermissions())) continue;
            sender.sendMessage(" §f- §b" + subCommand.getUsage(label) + " §f" + subCommand.getDescription());
        }
        sender.sendMessage("");
        sender.sendMessage("§7§m----------------------------------------");
    }
}