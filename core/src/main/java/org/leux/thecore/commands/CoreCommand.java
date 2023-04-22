package org.leux.thecore.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.leux.TheCore;
import org.leux.theapi.command.ICommand;
import org.leux.theapi.command.CommandResult;
import org.leux.theapi.command.ISubCommand;
import org.leux.theapi.utils.ColorUtils;
import org.leux.thecore.commands.subCore.InfoSubCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoreCommand extends ICommand implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin;

    public CoreCommand(String name, String description, List<String> aliases, List<ISubCommand> subCommands, boolean tabCompleter) {
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
        new CoreCommand(
                "thecore",
                "core kommando",
                null,
                Arrays.asList(new InfoSubCommand(TheCore.getInstance())),
                true
        );
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        CommandResult commandResult = super.execute(sender, args);
        switch (commandResult.getResult()) {
            case SUCCESS:
                return true;
            case WRONG_USAGE:
                sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix()) + " Brug: §b" + commandResult.getSubCommand().getUsage(label));
                return true;
            case NO_PERMISSION:
                sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix()) + " Du har ikke adgang til at bruge denne kommando.");
                return true;
            default:
                sendHelpMessage(sender, label);
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] strings) {
        return this.getAllowedSubCommands(commandSender, command, label, strings);
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
        sender.sendMessage(ColorUtils.getColored(TheCore.getPrefix()) + " §bCommands:");
        for (ISubCommand subCommand : super.getSubCommands()) {
            if (!hasPermission(sender, subCommand.getPermissions())) continue;
            sender.sendMessage(" §f- §b" + subCommand.getUsage(label) + " §f" + subCommand.getDescription());
        }
        sender.sendMessage("");
        sender.sendMessage("§7§m----------------------------------------");
    }
}