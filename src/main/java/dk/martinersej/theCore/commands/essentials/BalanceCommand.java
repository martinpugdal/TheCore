package dk.martinersej.theCore.commands.essentials;

import dk.martinersej.theapi.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class BalanceCommand extends Command {

    public BalanceCommand() {
        super("balance", new String[]{"bal"}, "Check your balance", "/balance");

        setPlayerOnly(true);
    }

    @Override
    public boolean run(CommandSender commandSender, String s, String... strings) {
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, String s, String[] strings) {
        return Collections.emptyList();
    }
}
