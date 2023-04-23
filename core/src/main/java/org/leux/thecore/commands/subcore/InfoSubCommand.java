package org.leux.thecore.commands.subcore;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.leux.TheCore;
import org.leux.theapi.command.CommandResult;
import org.leux.theapi.enums.Result;
import org.leux.theapi.command.SubCommand;

public class InfoSubCommand extends SubCommand {

    private final TheCore plugin;

    public InfoSubCommand(TheCore plugin) {
        super(plugin, "Information omkring PinkCore og serveren", "info", "thecore.command.info", "information", "info");
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSender sender, String[] args) {
        if (args.length != 0) {
            return Result.getCommandResult(this, Result.NO_SUB_COMMAND_FOUND);
        }

        if (!hasPermission(sender, getPermissions())) {
            return Result.getCommandResult(this, Result.NO_PERMISSION);
        }

        StringBuilder message = new StringBuilder();
        message.append("§7§m----------------------------------------");
        message.append("\n§b§lMinecraft: §f: ").append(plugin.getServer().getVersion());
        message.append("\n§b§lServer: §f: ").append(plugin.getServer().getBukkitVersion());
        message.append("\n§b§lTheCore §f: ").append(plugin.getDescription().getVersion());
        message.append("\n");
        if (TheCore.getDependants().size() > 0) {
            message.append("§b§lDependants §f: ");
            for (Plugin pl : TheCore.getDependants().values()) {
                message.append(" - ").append(pl.getName()).append(" (").append(pl.getDescription().getVersion()).append(")\n");
            }
            message.append("\n");
        }
        message.append("§7§m----------------------------------------");
        sender.sendMessage(message.toString());
        return Result.getCommandResult(this, Result.SUCCESS);
    }
}
