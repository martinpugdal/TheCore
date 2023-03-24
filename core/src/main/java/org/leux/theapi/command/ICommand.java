package org.leux.theapi.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ICommand {

    private final JavaPlugin plugin;
    private final ArrayList<ISubCommand> subCommands = new ArrayList<>();

    public ICommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    protected void addSubCommand(ISubCommand subCommand) {
        this.subCommands.add(subCommand);
    }

    protected boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    protected boolean isPlayer(CommandSender sender, String notPlayerMessage) {
        if (this.isPlayer(sender)) {
            return true;
        }

        sender.sendMessage(notPlayerMessage);
        return false;
    }

    protected boolean hasPermission(CommandSender sender, String... permissions) {
        for (String perm : permissions) {
            if (sender.hasPermission(perm)) {
                return true;
            }
        }
        return permissions.length == 0;
    }

    protected ISubCommand getSubCommandFromAlias(String alias) {
        for (ISubCommand subCommand : this.subCommands) {
            if (subCommand.containsAlias(alias)) {
                return subCommand;
            }
        }
        return null;
    }

    protected CommandResult execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            return new CommandResult(null, Result.NO_SUB_COMMAND_FOUND);
        }

        ISubCommand subCommand = getSubCommandFromAlias(args[0]);
        if (subCommand == null) {
            return new CommandResult(subCommand, Result.NO_SUB_COMMAND_FOUND);
        }

        if (!this.hasPermission(sender, subCommand.getPermissions())) {
            return new CommandResult(subCommand, Result.NO_PERMISSION);
        }

        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
        return subCommand.execute(sender, newArgs);
    }

    protected ArrayList<ISubCommand> getSubCommands() {
        return this.subCommands;
    }

    public List<String> getAllowedSubCommands(CommandSender commandSender, org.bukkit.command.Command command, String label, String[] strings) {
        ArrayList<String> allowedSubCommands = new ArrayList<>();
        for (ISubCommand subCommand : this.getSubCommands()) {
            if (hasPermission(commandSender, subCommand.getPermissions())) {
                allowedSubCommands.add(subCommand.getUsage(label));
            }
        }
        return allowedSubCommands;
    }
}
