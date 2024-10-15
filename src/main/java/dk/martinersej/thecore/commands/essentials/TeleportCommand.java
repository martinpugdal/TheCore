package dk.martinersej.thecore.commands.essentials;

import dk.martinersej.theapi.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.List;

public class TeleportCommand extends Command {

    public TeleportCommand() {
        super("Teleport", new String[]{"tp"}, "Teleport to a player", "/teleport <player>", "thecore.teleport");

        setPlayerOnly(true);
    }

    @Override
    public boolean run(CommandSender commandSender, String s, String[] strings) {
        if (strings.length == 0) {
            return false;
        }

        Player target = Bukkit.getPlayer(strings[0]);
        if (target == null) {
            commandSender.sendMessage("Spilleren findes ikke!");
        } else {
            teleport((Player) commandSender, target);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String s, String[] strings) {
        return defaultTabComplete(commandSender, s, strings);
    }

    void teleport(Player player, Player target) {
        player.teleport(target, PlayerTeleportEvent.TeleportCause.COMMAND);
        player.sendMessage("Teleporteret to " + target.getName());
    }
}
